package connect4;

import java.util.Scanner;
import java.util.function.*;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class Prompter<T> {
    private static final Scanner INPUT = new Scanner(System.in);

    private final String question; 
    private final String defaultValue;
    private final Predicate<String> checker;
    private final Function<String, T> mapper;

    private Prompter(Builder<T> builder) {
        this.question = builder.question;
        this.defaultValue = builder.defaultValue;
        this.checker = builder.checker;
        this.mapper = builder.mapper;
    }

    public T prompt() {
        while (true) {
            System.out.println(this.question);
            String answer = INPUT.nextLine().toLowerCase();
            if (this.defaultValue != null && answer.equals("")) {
                answer = defaultValue;
            };
            try {
                if (this.checker.test(answer)) {
                    return this.mapper.apply(answer);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static class Builder<T2> {
        private String question; 
        private String defaultValue;
        private Predicate<String> checker;
        private Function<String, T2> mapper;

        // STANDARD

        public Builder(String question) {
            this.question = question;
        }

        public Builder<T2> defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder<T2> checker(Predicate<String> checker) {
            this.checker = checker;
            return this;
        }

        public Builder<T2> mapper(Function<String, T2> mapper) {
            this.mapper = mapper;
            return this;
        }

        // SHORTCUTS
        
        public Builder<T2> choice(LinkedHashMap<String, T2> choiceMap) {
            List<String> questionChoices = new ArrayList<>();
            Map<String, T2> newChoiceMap = new HashMap<>();
            this.question += "\n  ";
            for (Map.Entry<String, T2> choice : choiceMap.entrySet()) {
                String lowerKey = choice.getKey().toLowerCase();
                String lowerKeyChar0 = lowerKey.charAt(0) + "";
                newChoiceMap.put(lowerKey, choice.getValue());
                newChoiceMap.put(lowerKeyChar0 + "", choice.getValue());
                questionChoices.add(String.format("(%s)%s",
                                    lowerKeyChar0,
                                    lowerKey.substring(1)));
            }
            this.question += String.join(", ", questionChoices);

            this.checker = answer -> newChoiceMap.containsKey(answer);
            this.mapper  = answer -> newChoiceMap.get(answer);
            return this;
        }

        public Builder<T2> mappedChecker(Predicate<T2> mappedChecker) {
            if (this.checker == null) this.checker = x -> true;
            // this escapes recursion 
            Predicate<String> tmp = this.checker;
            this.checker = answer ->
              tmp.test(answer) &&
              mappedChecker.test(this.mapper.apply(answer));
            return this;
        }

        public T2 prompt() {
            return build().prompt();
        }

        // THE BUILDER

        public Prompter<T2> build() {
            this.question += (this.defaultValue == null) 
            ? ": "
            : String.format(" (default: %s): ", this.defaultValue);
            // defaultValue SHOULD be null if unwanted
            if (this.checker == null) this.checker = x -> true;
            if (this.mapper == null) this.mapper = x -> (T2) x;
            //return new Prompter<T2>(this);
            return new Prompter<T2>(this);
        }

    }
}
