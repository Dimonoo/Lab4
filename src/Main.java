import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args)
    {
        long number = new Random().nextInt(500) + 500;
        String message1;
        boolean loop = true;
        Scanner in = new Scanner(System.in);
        List<Long> first = simpl(number);
        long a = getA(first);
        long p = getP(first);
        long n = getN(a, p);
        long f = getF(a, p);
        long d = getD(f);
        long e = getE(f, d);

        System.out.println("Открытый ключ = {" + d + ", " + n + "}"); //d n открытый ключ
        while (loop)
        {
            System.out.println("Алиса хочет отправить сообщение с текстом: ");
            System.out.println("");
            message1 = in.nextLine();
            char[] mesChar = message1.toCharArray();
            long resultChar;
            StringBuffer decod = new StringBuffer();
            List<Long> codMess = new ArrayList<>();

            for(int i = 0; i<mesChar.length;i++)
            { //перевод в char  и возведение в степень циклом
                int kodChar = (int) mesChar[i];
                resultChar = 1;
                for(int j = 1; j<=d; j++)
                {
                    resultChar = (kodChar * resultChar)%n;
                }
                codMess.add(resultChar);
            }
            System.out.println("Алиса отправляет: " );
            for(int i=0;i<codMess.size();i++)
            {
                System.out.print(Long.toHexString(codMess.get(i))); //выведение в 16ричной системе
            }
            System.out.println("\n");

            long result1Char;
            for(int i = 0; i<codMess.size();i++)
            {   //расшифровка через возведение в степень закрытого ключа e по модулю n
                result1Char = 1;

                for (int j = 0; j < e; j++)
                {
                    result1Char = (codMess.get(i) * result1Char) % n;
                }
                char cur = (char) result1Char;
                decod.append(cur);                        //e и n - закрытый ключ
            }
            System.out.println("Боб расшифровывает сообщение секретным ключом и получает: " + decod);
            System.out.println("Отправить еще сообщение? y/n");
            String s = in.nextLine();
            if (s.equals("n")) {
                loop = false;
            }
        }
    }

    private static List<Long> simpl(long number) {  //generate simple numbers
        List<Long> numbers = new ArrayList<>();
        long st = 0;
        long n = 3;
        numbers.add((long)2);
        while (st < number)
        {
            for (int i = 0; i < numbers.size(); i++)
            {
                if (n % numbers.get(i) == 0)
                {
                    break;
                }
                if (numbers.get(i) >= Math.sqrt(n))
                {
                    st++;
                    numbers.add(n);
                    break;
                }
            }
            n += (long) 2;
        }
        return numbers;
    }

    private static long getA(List<Long> numbers)
    {
        long a;
        Random random1 = new Random();
        a = numbers.get(random1.nextInt(numbers.size() - 2));
        return a;
    }

    private static long getP(List<Long> numbers)
    {
        long p = numbers.get(numbers.size()-1);
        return p;
    }
    private static long getN(long a, long p)
    {
        long n;
        n = a * p;
        return n;
    }

    private static long getF(long a, long p)
    {
        long f;
        f = (p - 1) * (a - 1);
        return f;
    }

    private static long getE(long f, long d)
    {
        long e = new Random().nextInt(11);
        while ((((e * d) % f) !=1) || (e==d))
        {
            e++;
        }
        //System.out.println(e);
        return e;
    }

    private static long getD(long f){
        int er = new Random().nextInt(30000);
        int r = (int)(Math.random()*er);
        long d = simpl(er).get(er-r);
        for (long i = 2; i <= f; i++)
        {
            if ((f % i == 0) && (d % i == 0))
            {
                r--;
                d = simpl(er).get( er - r);
                i = 1;
            }
        }
        return d;
    }
}