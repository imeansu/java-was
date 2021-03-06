package framework.template;

import framework.modelAndView.Model;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class TemplateParser2 {

    public static Pattern TAG_TYPE_PATTERN = Pattern.compile("\\{\\{.+}}");
    public static Pattern BLOCK_OPEN_PATTERN = Pattern.compile("\\{\\{#.+}}");
    public static Pattern BLOCK_CLOSE_PATTERN = Pattern.compile("\\{\\{/.+}}");

    public static String getTemplateWithModel(String template, Model model) throws IOException {
        StringBuilder sb = new StringBuilder();
        Integer index = 0;

        stackRenderModel(template, sb, index, model);
        return sb.toString();
    }

    public static void stackRenderModel(String template, StringBuilder sb, Integer index, Model model) {
        Stack<Object> modelStack = new Stack<>();
        modelStack.add(model);

        char pre = 0;
        char cur;
        while (index < template.length()) {
            if ((cur = template.charAt(index)) != '{') {
                if (pre != 0) {
                    sb.append(pre);
                    sb.append(cur);
                } else {
                    sb.append(cur);
                }
                index++;
                continue;
            }
            if (cur == '{') {
                if (pre != 0 && pre == '{') {
                    switch (template.charAt(++index)) {
                        case '#':
                            String objectAttributeName = getAttribueByName(template, ++index, model);
                            index += recursiveProcessBlock(template, sb, index, model.getInAttribute(objectAttributeName));
                            break;
                        case '/':

                        default:
                            String stringAttributeName = getAttribueByName(template, index, model);
                            index += processTag(template, sb, index, stringAttributeName, modelStack.peek());
                    }
                    pre = 0;
                } else {
                    pre = cur;
                    index++;
                    continue;
                }
            }


        }
    }

    private static Integer recursiveProcessBlock(String template, StringBuilder sb, Integer index, Object inAttribute) {
        return null;
    }

    private static Integer processTag(String template, StringBuilder sb, Integer index, String target, Object model) {
        String stringAttribute = null;

        if (model instanceof Model)
            stringAttribute = ((Model) model).getInAttribute(target).toString();
        if (model instanceof List)

//        index -= 2;
//        template.replace(String.format("{{%s}}", target), stringAttribute);
        sb.append(stringAttribute);
        return target.length() + 2;

    }

    private static String getAttribueByName(String template, Integer startIndex, Model model) {
        int end = template.indexOf("}}", startIndex);
        String attributeName = template.substring(startIndex, end);
        startIndex = end + 2;
        return attributeName;
    }

}
