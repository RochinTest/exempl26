import java.util.regex.Pattern;
import java.util.stream.Stream;

//В американской армии считается несчастливым число 13, а в японской – 4. Перед международными
// учениями штаб российской армии решил исключить номера боевой техники, содержащие числа 4 или 13
// (например, 40123, 13313, 12345 или 13040), чтобы не смущать иностранных коллег. Если в
// распоряжении армии имеется 100 тыс. единиц боевой техники и каждая боевая машина имеет номер
// от 00001 до 99999, то сколько всего номеров придётся исключить?
public class Main {
    private final static Pattern p4 = Pattern.compile("[0-35-9]{1,5}");
    private final static Pattern p13 = Pattern.compile(".*13.*");

    public static void main(String[] args) {
        method0();
        method1();
        method2();
        method3();
        method4();
        method5();
    }

    private static void method0() {
        System.out.print("Method 0 -> ");
        final long start = System.currentTimeMillis();
        int[] s = new int[5];
        //int lot = 1;
        int sam = 0;
        int m;
        for (int j = 0; j <= 99999; j++) {
            m = j;
            for (int i = 0; m != 0; m /= 10, i++) {
                s[i] = (m % 10);
                if (s[i] == 4) {
                    sam = sam + 1;
                    break;
                }
                if (s[i] == 1)
                    if (i > 0 && s[i - 1] == 3) {

                        sam = sam + 1;
                        break;
                    }
            }
        }
        System.out.println("Count " + sam + ", Time: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void method1() {
        System.out.print("Method 1 -> ");
        final long start = System.currentTimeMillis();

        int wrongCount = 0;
        for (int i = 0; i <= 99999; i++) {
            if (checkCondition1(Integer.toString(i).toCharArray())) {
                wrongCount++;
            }
        }

        System.out.println("Count " + wrongCount + ", Time: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void method2() {
        System.out.print("Method 2 -> ");
        final long start = System.currentTimeMillis();

        int wrongCount = 0;
        for (int i = 0; i <= 99999; i++) {
            final String s = Integer.toString(i);
            if (!p4.matcher(s).matches() || p13.matcher(s).matches()) {
                wrongCount++;
            }
        }

        System.out.println("Count " + wrongCount + ", Time: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void method3() {
        System.out.print("Method 3 -> ");
        final long start = System.currentTimeMillis();

        System.out.println("Count " + Stream.iterate(0, i -> i + 1)
                .limit(99999)
                .map(Object::toString)
                .filter(s -> !p4.matcher(s).matches() || p13.matcher(s).matches())
                .count()
                + ", Time: " + (System.currentTimeMillis() - start) + " ms"
        );
    }

    private static void method4() {
        System.out.print("Method 4 -> ");
        final long start = System.currentTimeMillis();

        System.out.println("Count " + Stream.iterate(0, i -> i + 1)
                .limit(99999)
                .map(Object::toString)
                .map(String::toCharArray)
                .filter(Main::checkCondition1)
                .count()
                + ", Time: " + (System.currentTimeMillis() - start) + " ms"
        );
    }

    private static void method5() {
        System.out.print("Method 5 -> ");
        final long start = System.currentTimeMillis();

        System.out.println("Count " + Stream.iterate(0, i -> i + 1)
                .limit(99999)
                .filter(Main::checkCondition2)
                .count()
                + ", Time: " + (System.currentTimeMillis() - start) + " ms"
        );
    }

    private static boolean checkCondition1(final char[] chars) {
        for (int j = 0; j < chars.length; j++) {
            if ('4' == chars[j] || '1' == chars[j] && j + 1 < chars.length && '3' == chars[j + 1]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkCondition2(final int i) {
        int m = i;
        int prev = 0;
        int current;
        for (int j = 0; m != 0; m /= 10, j++) {
            current = (m % 10);
            if (current == 4 || current == 1 && j > 0 && prev == 3) {
                return true;
            }
            prev = current;
        }
        return false;
    }
}