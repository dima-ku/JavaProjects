package expression.exceptions;


import expression.*;

import java.util.Arrays;
import java.util.List;

public final class ExpressionParser implements TripleParser {
    public ExpressionWithPriority parse(final String source) {
        return parse(new StringSource(source));
    }

    public static ExpressionWithPriority parse(final CharSource source) {
        return new ExpressionParserImplementation(source).parseExpression();
    }

    private static class ExpressionParserImplementation extends BaseParser {

        List<List<String>> operations = Arrays.asList(
                Arrays.asList("gcd", "lcm"),
                Arrays.asList("+", "-"),
                Arrays.asList("*", "/")
        );

        public ExpressionParserImplementation(final CharSource source) {
            super(source);
        }
        public ExpressionWithPriority parseExpression() {
            final ExpressionWithPriority result = parseWithPriority(0);
            skipWhitespace();
            if (test(')')) {
                throw error(getPos() + ": " + "Extra close brackets");
            }
            if (eof()) {
                return result;
            }
            throw error("Expected end of file, but actual: " + get());
        }

        private ExpressionWithPriority parseWithPriority(int priority) {
            skipWhitespace();
            ExpressionWithPriority start = getDeeper(priority);
            while (true) {
                skipWhitespace();
                if (eof() || test(')')) {
                    return start;
                }
                String op = getOperation();
                if (checkExist(priority, op)) {
                    start = getExpression(op, start, getDeeper(priority));
                } else {
                    back(op.length());
                    return start;
                }
            }
        }

        ExpressionWithPriority getDeeper(int priority) {
            if (priority == operations.size() - 1) {
                return parseStart();
            } else {
                return parseWithPriority(priority + 1);
            }
        }
        private String getOperation() {
            skipWhitespace();
            for (List<String> list : operations) {
                for (String op : list) {
                    if (check(op)) {
                        take(op.length());
                        if (op.equals("lcm") || op.equals("gcd")) {
                            checkForCorrectEnd(op);
                        }
                        return op;
                    }
                }
            }
            throw new OperationNotFoundException(getPos() + ": " + " Operation not found");
        }
        private ExpressionWithPriority getExpression(String op, ExpressionWithPriority first,
                                                     ExpressionWithPriority second) {
            return switch (op) {
                case "+" -> new CheckedAdd(first, second);
                case "-" -> new CheckedSubtract(first, second);
                case "*" -> new CheckedMultiply(first, second);
                case "/" -> new CheckedDivide(first, second);
                case "lcm" -> new CheckedLcm(first, second);
                case "gcd" -> new CheckedGcd(first, second);
                default -> throw new IllegalArgumentException("Incorrect operation given to function getExpression");
            };
        }
        private ExpressionWithPriority parseStart() {
            skipWhitespace();
            if (test('(')) {
                take();
                ExpressionWithPriority result = parseWithPriority(0);
                skipWhitespace();
                expect(')', "No closing brackets: expected: ')' actual: " + get());
                return result;
            }
            if (test('r') || test('l') || test('p')) {
                return checkForUnaryOperation();
            }
            StringBuilder sb = new StringBuilder();
            if (test('-')) {
                take();
                if (!between('0', '9')) {
                    return new CheckedNegate(parseStart());
                }
                sb.append("-");
            }
            if (between('0', '9') || sb.toString().equals("-")) {
                takeDigits(sb);
                return new Const(tryParseInt(sb.toString()));
            }
            skipWhitespace();
            char tmp = get();
            return switch (take()) {
                case 'x' -> new Variable("x");
                case 'y' -> new Variable("y");
                case 'z' -> new Variable("z");
                default -> throw new ParseOperandException(getPos() + ": " + " operand isn't correct");
            };
        }

        public int tryParseInt(String s) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new ParsingConstException(getPos() + ": " + "Problem with parsing const: " + s);
            }
        }

        public ExpressionWithPriority checkForUnaryOperation() {
            if (test('r')) {
                expect("reverse");
                checkForCorrectEnd("reverse");
                return new CheckedReverse(parseStart());
            }
            if (test('l')) {
                expect("log10");
                checkForCorrectEnd("log10");
                return new CheckedLog10(parseStart());
            }
            if (test('p')) {
                expect("pow10");
                checkForCorrectEnd("pow10");
                return new CheckedPow10(parseStart());
            }
            throw new IllegalArgumentException("expected l - log10 p - pow10 r - reverse, actual: " + get());
        }

        public void checkForCorrectEnd(String expected) {
            if (Character.isLetter(get()) || between('0', '9')) {
                throw new ParseOperationException(getPos() + ": " + "Expected: " + expected
                       + " but actual: " + expected + get());
            }
        }
        public void take(int n) {
            for (int i = 0; i < n; i++) {
                take();
            }
        }
        private boolean check(String str) {
            for (int i = 0; i < str.length(); i++) {
                if (take() != str.charAt(i)) {
                    back(i + 1);
                    return false;
                }
            }
            back(str.length());
            return true;
        }
        private boolean checkExist(int priority, String op) {
            if (priority >= operations.size() || priority < 0) {
                return false;
            }
            for (int i = 0; i < operations.get(priority).size(); i++) {
                if (operations.get(priority).get(i).equals(op)) {
                    return true;
                }
            }
            return false;
        }
        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private void expect(char c, String message) {
            if (!test(c)) {
                throw error(message);
            }
            take();
        }
        private void skipWhitespace() {
            while (Character.isWhitespace(get())) {
                take();
            }
        }
    }
}
