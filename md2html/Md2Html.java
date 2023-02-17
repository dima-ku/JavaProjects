package md2html;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Md2Html {
    private final static Map<String, String> HTML_SYMBOLS = new HashMap<>(Map.of(
        "<", "&lt;",
        ">", "&gt;",
        "&", "&amp;"
    ));

    private final static Map<String, String> HTML_TAG = new HashMap<>(Map.of(
        "_", "em",
        "*", "em",
        "__", "strong",
        "**", "strong",
        "`", "code",
        "--", "s"
    ));

    private final static String[] TAGS = new String[]{"_", "__", "*", "**", "--", "`"};

    public static void main(final String[] args) {
        final String input = args[0];
        final String output = args[1];
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final InputStreamReader reader = new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8);
            try {
                final char[] buffer = new char[1024];
                int read = reader.read(buffer);
                while (read >= 0) {
                    stringBuilder.append(buffer, 0, read);
                    read = reader.read(buffer);
                }
            } finally {
                reader.close();
            }
        } catch (final FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (final SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        } catch (final IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }

        StringBuilder token = new StringBuilder();
        final List<StringBuilder> elements = new ArrayList<>();

        for (int i = 0; i < stringBuilder.length(); i++) {
            token.append(stringBuilder.charAt(i));
            if (afterDoubleSeparator(stringBuilder, i) || i == stringBuilder.length() - 1) {
                elements.add(strip(token));
                token = new StringBuilder();
            }
        }

        final StringBuilder answer = new StringBuilder();
        for (final StringBuilder element : elements) {
            answer.append(toHtml(element, true));
        }

        try {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(output),
                StandardCharsets.UTF_8
            ));
            try {
                writer.write(answer.toString());
            } finally {
                writer.close();
            }
        } catch (final FileNotFoundException e) {
            System.out.println("Output file not found: " + e.getMessage());
        } catch (final SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        } catch (final IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }
    }


    private static String toHtml(final StringBuilder markdown, final boolean withTag) {
        final Map<String, Integer> countOfTags = new HashMap<>(Map.of(
            "_", 0,
            "__", 0,
            "*", 0,
            "**", 0,
            "--", 0,
            "`", 0
        ));

        final Map<String, Integer> used = new HashMap<>(Map.of(
            "_", 0,
            "__", 0,
            "*", 0,
            "**", 0,
            "--", 0,
            "`", 0
        ));

        for (int i = 0; i < markdown.length(); i++) {
            if (!(i - 1 > 0 && markdown.charAt(i - 1) == '\\') && i + 1 < markdown.length()
                && countOfTags.containsKey(markdown.substring(i, i + 2))) {
                countOfTags.put(
                    markdown.substring(i, i + 2),
                    countOfTags.getOrDefault(markdown.substring(i, i + 2), 0) + 1
                );
            } else if (!(i - 1 > 0 && markdown.charAt(i - 1) == '\\') &&
                countOfTags.containsKey(markdown.substring(i, i + 1))) {
                countOfTags.put(
                    markdown.substring(i, i + 1),
                    countOfTags.getOrDefault(markdown.substring(i, i + 1), 0) + 1
                );
            }
        }
        final StringBuilder html = new StringBuilder();
        final StringBuilder openTag = new StringBuilder();
        final StringBuilder closeTag = new StringBuilder();
        int countOfHeaderTagInCurrentElement = 0;
        int i = 0;
        int j = 0;
        while (j < markdown.length() && markdown.charAt(j) == '#') {
            countOfHeaderTagInCurrentElement++;
            j++;
        }
        if (countOfHeaderTagInCurrentElement > 0 && j < markdown.length()
            && Character.isWhitespace(markdown.charAt(j))) {
            openTag.append("<h").append(countOfHeaderTagInCurrentElement).append(">");
            closeTag.append("</h").append(countOfHeaderTagInCurrentElement).append(">");
            i += countOfHeaderTagInCurrentElement + 1;
        } else {
            openTag.append("<p>").append("#".repeat(countOfHeaderTagInCurrentElement));
            closeTag.append("</p>");
            i += countOfHeaderTagInCurrentElement;
        }
        for (; i < markdown.length(); i++) {
            if (markdown.charAt(i) == '\\' && i + 1 < markdown.length()) {
                html.append(markdown.charAt(i + 1));
                i++;
            } else if (markdown.charAt(i) == '[' && linkIsValid(markdown, i)) {
                i = parseLink(markdown, html, i);
            } else {
                i = parse(markdown, html, i, countOfTags, used);
            }
        }

        if (!onlyWhitespaces(html) && withTag) {
            return openTag.toString() + html + closeTag + System.lineSeparator();
        } else {
            return html.toString();
        }
    }

    private static int parse(
        StringBuilder markdown, StringBuilder html, int position,
        Map<String, Integer> countOfTags, Map<String, Integer> used
    ) {
        int i = position;
        String tag = "";
        if (i + 1 < markdown.length() && countOfTags.containsKey(markdown.substring(i, i + 2))) {
            tag = markdown.substring(i, i + 2);
        } else if (countOfTags.containsKey(markdown.substring(i, i + 1))) {
            tag = markdown.substring(i, i + 1);
        }
        if (tag.isEmpty()) {
            addPart(html, markdown.charAt(i));
        } else {
            final int useNo = used.get(tag);
            if (useNo % 2 == 0 && countOfTags.get(tag) - useNo >= 2) {
                html.append("<").append(HTML_TAG.get(tag)).append(">");
                used.put(tag, useNo + 1);
            } else if (useNo % 2 == 1) {
                html.append("</").append(HTML_TAG.get(tag)).append(">");
                used.put(tag, useNo + 1);
            } else {
                html.append(tag);
            }
        }
        if (tag.length() == 2) {
            i++;
        }
        return i;
    }

    private static boolean onlyWhitespaces(StringBuilder stringBuilder) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (!Character.isWhitespace(stringBuilder.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean linkIsValid(StringBuilder markdown, int position) {
        for (int i = position + 1; i < markdown.length() - 1; i++) {
            if (markdown.charAt(i) == ']' && markdown.charAt(i + 1) == '(') {
                for (int j = i + 2; j < markdown.length(); j++) {
                    if (markdown.charAt(j) == ')') {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private static int parseLink(StringBuilder markdown, StringBuilder html, int position) {
        int i = position;
        final StringBuilder contentOfLink = new StringBuilder();
        final StringBuilder link = new StringBuilder();
        i++;
        while (!(markdown.charAt(i) == ']' && (i + 1 < markdown.length() && markdown.charAt(i + 1) == '('))) {
            contentOfLink.append(markdown.charAt(i));
            i++;
        }
        i += 2;
        while (markdown.charAt(i) != ')') {
            link.append(markdown.charAt(i));
            i++;
        }
        html
            .append("<a href='")
            .append(link)
            .append("'>")
            .append(toHtml(contentOfLink, false))
            .append("</a>");
        return i;
    }

    private static void addPart(final StringBuilder stringBuilder, final char symbol) {
        if (HTML_SYMBOLS.containsKey(Character.toString(symbol))) {
            stringBuilder.append(HTML_SYMBOLS.get(Character.toString(symbol)));
        } else {
            stringBuilder.append(symbol);
        }
    }

    private static boolean isLineSeparator(final StringBuilder stringBuilder, final int position) {
        final int lengthSeparator = System.lineSeparator().length();
        if (position >= lengthSeparator - 1) {
            return System.lineSeparator().equals(stringBuilder.substring(position - lengthSeparator + 1, position + 1));
        } else {
            return false;
        }
    }

    private static boolean afterDoubleSeparator(final StringBuilder stringBuilder, final int position) {
        return isLineSeparator(stringBuilder, position) &&
            isLineSeparator(stringBuilder, position - System.lineSeparator().length());
    }

    private static StringBuilder rstrip(final StringBuilder stringBuilder) {
        int index = stringBuilder.length() - 1;
        final int size = System.lineSeparator().length();
        final StringBuilder result = new StringBuilder();
        while (index >= 0 && isLineSeparator(stringBuilder, index)) {
            index -= size;
        }
        result.append(stringBuilder.substring(0, index + 1));
        return result;
    }

    private static StringBuilder lstrip(final StringBuilder stringBuilder) {
        int index = System.lineSeparator().length() - 1;
        while (index < stringBuilder.length() && isLineSeparator(stringBuilder, index)) {
            index += System.lineSeparator().length();
        }
        return new StringBuilder(stringBuilder.substring(
            index - (System.lineSeparator().length() - 1),
            stringBuilder.length()
        ));
    }

    private static StringBuilder strip(final StringBuilder stringBuilder) {
        final StringBuilder result = rstrip(lstrip(stringBuilder));
        return result;
    }

}
