package com.sqs.test;

import java.util.*;

/**
 * Created by SQS19911128 on 2020/12/4.
 */
public class Test {

    public static void shuru(){
        Scanner in=new Scanner(System.in);
        System.out.println("please enter a: ");
        int a=in.nextInt();//这是输入a
        System.out.println("Please enter b: ");
        int b=in.nextInt();//这是输入b
        int c=a+b;
        System.out.println("The result:"+c);//输出结果zhi。
    }
    /* 数字类 */
    //1.回文数相加，例：输入2 输出1+121；输入3 输出1+121+12321
    public static int addBackNumber(int n){
        if(n<=0){
            return 0;
        }
        if(n==1){
            return 1;
        }else{
            StringBuilder list=new StringBuilder();
            for(int i=1;i<=n;i++){
                list.append(i);
            }
            for(int i=n-1;i>0;i--){
                list.append(i);
            }
            String s = list.toString();
            int result = Integer.parseInt(s);
            return result+addBackNumber(n-1);
        }
    }
    //2.找出一个数字中的偶数相加
    public static int addEvenNumber(int n){
        char[] chars = String.valueOf(n).toCharArray();
        int sum=0;
        int length=chars.length;
        for (char c : chars) {
//        for(int i=0;i<length;i++){
//            char c=chars[i];
            int a=Integer.parseInt(String.valueOf(c));
            if(a%2==0){
                sum+=a;
            }
        }
        return sum;
    }
    //3.输入一个整数，取出这个整数中的偶数位上的数字组成一个新数并输出，例如输入123456，输出246
    public static int createEvenNumber(int n){
        char[] chars = String.valueOf(n).toCharArray();
        StringBuilder sb=new StringBuilder();
        int length=chars.length;
        for (char c : chars) {
//        for(int i=0;i<length;i++){
//            char c=chars[i];
            int a=Integer.parseInt(String.valueOf(c));
            if(a%2==0){
                sb.append(c);
            }
        }
        return Integer.parseInt(sb.toString());
    }
    //4.一元钱买一瓶汽水，两个空汽水瓶换一瓶汽水，输入你的钱数，问你能喝多少瓶汽水
    public static int getNumOfDrink(int money,int bottle){
        bottle = money + bottle;
        if(bottle / 2 == 0) {
            return money;
        }
        return money + getNumOfDrink(bottle / 2 , bottle % 2);
    }
    //5.一元钱买一瓶汽水，两个空汽水瓶换一瓶汽水，三个瓶盖换一瓶汽水，输入你的钱数，问你能喝多少瓶汽水
    public static int getNumOfDrink(int money,int bottle,int cap){
        bottle = money + bottle;
        cap=money+cap;
        if(bottle/2+cap/3 == 0) {
            return money;
        }
        return money + getNumOfDrink(bottle / 2+cap/3 , bottle % 2,cap%3);
    }
    //6.求最大公约数
    public static int getGcd(int n1,int n2){
        if(n2==0){
            return n1;
        }
        return getGcd(n2,n1%n2);
    }
    //7.输入 n 和 b , 找出 1 到 n 中被 b 整除的个数.
    public static int getNumber(int n,int b){
        int count=0;
        for(int i=1;i<=n;i++){
            if(i%b==0) {
                count++;
            }
        }
        return count;
    }
    //8.N的阶乘
    public static int getN(int n){
        int result=1;
        if(n<=0){
            return 0;
        }else{
            for(int i=1;i<=n;i++){
                result=result*i;
            }
            return result;
        }
//        if(n<=2) {
//            return n;
//        }else {
//            int[] num = new int[n];
//            num[0]=1;
//            num[1]=2;
//            for(int i=2;i<n;i++){
//                num[i]=(i+1)*num[i-1];
//            }
//            return num[n-1];
//        }
    }
    //9.输入十个数，最大数和最后一个数交换，最小数和第一个数交换
    public static int[] numberChange(int[] array){
        int minIndex=0;
        int min=array[0];
        int maxIndex=0;
        int max=array[0];
        for (int i = 0; i < array.length; i++) {
            if(array[i]<min){
                min=array[i];
                minIndex=i;
            }
            if(array[i]>max){
                max=array[i];
                maxIndex=i;
            }
        }
        int temp1=array[0];
        array[0]=min;
        array[minIndex]=temp1;
        int temp2=array[array.length-1];
        array[array.length-1]=max;
        array[maxIndex]=temp2;
        return array;
    }
    //10.连续子数组的最大和
    public static int FindGreatestSumOfSubArray(int[] array) {
        if(array.length==0) {
            return 0;
        }
        int total=array[0];
        int maxSum=array[0];
        for(int i=1;i<array.length;i++){
            total=(total>0)?total+array[i]:array[i];
            maxSum= Math.max(maxSum, total);
        }
        return maxSum;
    }
    //11.从数组中找出唯一出现一次的数
    public static int findOnlyNum(int[] arr){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int number : arr) {
            if(!map.containsKey(number)) {
                map.put(number, 1);
            }else {
                int val = map.get(number);
                map.replace(number,val,val+1);
            }
        }
        Set<Integer> keySet = map.keySet();
        for (Integer integer : keySet) {
            if(map.get(integer)==1) {
                return integer;
            }
        }
        return -1;
    }
    //12.约瑟夫环，输入人数和间隔数，输出顺序
    public static void Josephus(int num,int k){
        //下标从1开始
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=1;i<=num;i++){
            list.add(i);
        }
        int target=0;
        while (!list.isEmpty()){
            target=(target+k)%list.size();
            if(target!=0){
                System.out.println(list.get(target-1)+"被处死");
                list.remove(target-1);
                target--;
            }
            else {
                System.out.println(list.get(list.size()-1)+"被处死");
                list.remove(list.size()-1);
            }
        }
    }
    //13.给一个8元素数组例如1 3 0 3 6 0 0 9将所有0放后面，其他数字顺序不变，结果为1 3 3 6 9 0 0 0
    public static int[] removeZero(int[] arr){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i : arr) {
            if(i!=0) {
                list.add(i);
            }
        }
        //补充0
        int[] result=new int[8];
        for(int i=0;i<list.size();i++){
            result[i]=list.get(i);
        }
        return result;
    }

    /* 判断类 */
    //1.判断素数,因数只有1和自己
    public static boolean isPrimeNumber(int n){
        if(n==1) {
            return false;
        }
        if(n==2) {
            return true;
        }
        for(int i=2;i<n/2+1;i++){
            if(n%i==0) {
                return false;
            }
        }
        return true;
    }
    //2.输入两个年份之间的闰年年份
    public static void printYear(int y1, int y2){
        for(int i=y1;i<=y2;i++){
            if(i%400==0||(i%4==0&&i%100!=0))
                System.out.print(i+",");
        }
    }
    //3.给你年月日，求出是这年的第几天
    public static int getDaynumberOfYear(int year,int month,int day){
        boolean isYear=false;
        if(year%400==0||(year%4==0&&year%100!=0)) {
            isYear = true;
        }
        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        if(isYear) {
            days[1] = 29;
        }
        int sum=0;
        for(int i=0;i<month-1;i++){
            sum+=days[i];
        }
        sum+=day;
        return sum;
    }
    //4.每次爬1个或者2个台阶，输入 1 <= n < 90 的数字为台阶数，以输入 0 作为结束标志，输出n个台阶共有多少种上楼方式
    //n级阶梯共有m种走法。因为可以先上n-2级，在第n-2级基础上再向上一步走2级；可以先上n-1级，在第n-1级基础上再向上一步走1级。
    public static int numOfFloor(int n){
        if(n<=2) {
            return n;
        }else {
            int[] num=new int[n];
            num[0]=1;
            num[1]=2;
            for(int i=2;i<n;i++){
                num[i]=num[i-1]+num[i-2];
            }
            return num[n-1];
        }
    }
    //5.一只小猴子一天摘了许多桃子，第一天吃了一半，然后忍不住又吃了一个；第二天又吃了一半，再加上一个；后面每天都是这样吃。
    // 到第10天的时候，小猴子发现只有一个桃子了。问小猴子第一天共摘了多少个桃子
    public static int NumOfPeach(){
        int left=1;
        for(int i=9;i>0;i--){
            left=(left+1)*2;
        }
        return left;
    }
    //6.给三个数abc，能否在1000-9999之间找到一个数x，满足x%a=0且（x+1）%b=0且（x+2）%c=0，找不到这个数x就返回Impossible
    public static String findNumber(int a, int b, int c){
        for(int i=1000;i<=9999;i++){
            if((i%a==0)&&((i+1)%b==0)&&((i+2)%c==0)) {
                return "can";
            }
        }
        return"Impossible";
    }
    //7.判断两个字符串是否是异位(位置不同)
    public static boolean isDifferentPosition(String s1,String s2){
        char[] array1 = s1.toCharArray();
        char[] array2= s2.toCharArray();
        Arrays.sort(array1);
        Arrays.sort(array2);
        for (int i = 0; i < array1.length; i++) {
            if(array1[i]!=array2[i]) {
                return false;
            }
        }
        return true;
    }

    /* 字符串类 */
    //1.输入一个只包含字母的字符串，将字符串中的大写字母改为小写字母，将小写字母改为大写字母，并输出
    public static String changeStr(String s){
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if ('a' <= c[i] && c[i] <= 'z') {
                System.out.println("小写换大写");
                c[i] = (char) (c[i] - 32);
                continue;
            }
            if ('A' <= c[i] && c[i] <= 'Z'){
                System.out.println("大写换小写");
                c[i] = (char) (c[i] + 32);
                continue;
            }
        }
        return  String.valueOf(c);
    }
    //2.字符串加密,把字符串中的字符a和A换成c输出。
    public static String toSecret(String s){
        char[] chars = s.toCharArray();
        StringBuilder re= new StringBuilder();
        for (char aChar : chars) {
            if(aChar=='a'||aChar=='A') {
                re.append('c');
            }else {
                re.append(aChar);
            }
        }
        return re.toString();
    }
    //3.字符串反转
    public static String reverse(String s){
        char[] chars = s.toCharArray();
        StringBuilder re= new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            re.append(chars[i]);
        }
        return re.toString();
    }
    //4.最长公共前缀 leetcode14
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }
        if(strs.length == 1){
            return strs[0];
        }
        int len = strs[0].length();
        for(String str:strs){
            len = Math.min(len,str.length());  //得到字符数组里面最短字符的长度
        }
        if(len == 0){  //其中的一个字符为空的情况
            return " ";
        }
        StringBuffer res = new StringBuffer(len);
        //每个字符只需要遍历到最小长度即可，因为大于最小长度字符的那一部分不可能有公共
        for(int j = 0; j < len;j++){
            for(int t= 0;t < strs.length;t++){
                if(strs[t].charAt(j) != strs[0].charAt(j)){ //每个与第一个字符开始比较，也就是与下标为0的字符的元素比较。
                    return res.toString();
                }
            }
            res.append(strs[0].charAt(j)); //相同就加进结果集
        }
        return res.toString();
    }
    public static String longestCommonPrefix1(String[] strs) {
        int count = strs.length;
        String prefix = "";
        if(count != 0){
            prefix = strs[0];
        }
        for(int i=0; i<count; i++){
            //关键代码，不断的从后往前截取字符串，然后与之相比，直到startsWith()返回true
            while(!strs[i].startsWith(prefix)){
                prefix = prefix.substring(0, prefix.length()-1);
            }
        }
        return prefix;
    }
    public static String longestSameStr(String[] strs){
        if(strs.length==0) {
            return "";
        }
        String s=strs[0];
        for(int i=1;i<strs.length;i++){
            while (strs[i].indexOf(s)!=0){
                s=s.substring(0,s.length()-1);
            }
        }
        return s;
    }
    //5.输入几个单词，将字母变换成另外一组单词输出？如果字母是字母表第i个，则变换后的字母是字母表第26-i+1个
    //A的ASCII码是65,a的ASCII码是97
    public static String changeStr2(String s){
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if('a'<=c[i]&&c[i]<='z') {
//                c[i] = (char)('a'*2+25-c[i]);
                c[i] = (char)(26-(c[i]-96)+1+96);
            }
            if('A'<=c[i]&&c[i]<='Z') {
//                c[i] = (char) ('A'*2+25-c[i]);
                c[i] = (char)(26-(c[i]-64)+1+64);
            }
        }
        return  String.valueOf(c);
    }
    //6.A,B两个字符串,求在第一个字符串出现,第二个字符串中未出现的,重复只取第一次出现,输出字符串
    public static String getStrOfStrings(String s1,String s2){
        //将第一个字符串中不重复的存入char集合
        ArrayList<Character> chars=new ArrayList<>();
        char[] c1 = s1.toCharArray();
        for (char c : c1) {
            if(!chars.contains(c)) {
                chars.add(c);
            }
        }
        System.out.println(chars.toString());
        //将第二个字符串存入chars2集合
        ArrayList<Character> chars2=new ArrayList<>();
        char[] c2 = s2.toCharArray();
        for (char c : c2) {
            chars2.add(c);
        }
        System.out.println(chars2.toString());
        //如果不包含则存入结果
        StringBuilder sb=new StringBuilder();
        for (Character character : chars) {
            if(!chars2.contains(character))
                sb.append(character);
        }
        return sb.toString();
    }
    //7.输出字符串的简称，比如字符串是"end of file"，输出"EOF"
    public static String getSimpleStr(String s){
        String[] s1 = s.split(" ");
        StringBuilder sb=new StringBuilder();
        for (String s2 : s1) {
            char[] chars = s2.toCharArray();
            char aChar = chars[0];
            sb.append(aChar);
        }
        String result = sb.toString().toUpperCase();
        return result;
    }
    //8.字符串移除奇数位置字符
    public static String removeStr(String s){
        char[] c = s.toCharArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 1; i < c.length; i=i+2) {
            sb.append(c[i]);
        }
        return sb.toString();
    }
    //9.判断字符串括号是否匹配
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();   //创建一个栈
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == '(') || (s.charAt(i) == '[') || (s.charAt(i) == '{')) {    // 如果是左括号，则将其放入栈内
                stack.push(s.charAt(i));
            }
            if ((s.charAt(i) == ')') || (s.charAt(i) == ']') || (s.charAt(i) == '}')) {   // 如果是右括号
                if (stack.empty()) {     //  如果栈为空，则证明括号不匹配，返回false
                    return false;
                }
                if ((stack.peek() == '(' && s.charAt(i) == ')') || (stack.peek() == '[' && s.charAt(i) == ']') || (stack.peek() == '{' && s.charAt(i) == '}') ) {     // 如果栈顶元素和下一个右括号相匹配，则将其栈顶元素出栈
                    stack.pop();
                }
            }
        }
        if (stack.empty()) {   // 遍历循环结束后，如果发现栈里为空，则证明括号匹配完毕；否则括号不匹配
            return true;
        }
        return false;
    }

    /* 进制类 */
    //1.输入两个整数M和N,输出两个数转化为二进制位不同的个数
    public static int differentNumOf2Number(int m,int n){
        //异或 不同为1 相同为0 再统计1的个数
        int i = m^n;
        int count=0;
        char[] c = Integer.toBinaryString(i).toCharArray();
        for(int j=0;j<c.length;j++){
            if(c[j]=='1')
                count++;
        }
        return count;
    }
    //2.10进制转换k进制
    public static String getBNumber(int n,int k){
        ArrayList<Integer> list=new ArrayList();
        int n1=n;
        StringBuilder re= new StringBuilder();
        while(n1!=0){
            list.add(n1%k);
            n1/=k;
        }
        Object[] array = list.toArray();
        for(int i=array.length-1;i>=0;i--){
            re.append(array[i]);
        }
        System.out.println(n+"的"+k+"进制形式为："+re);
        return re.toString();
    }
    //3.k进制转换10进制
    public static void getTNumber(String s,int k){
        char[] chars = s.toCharArray();
        int n=0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if(chars[i]=='1')
                n+=Math.pow(k,chars.length-i-1);
        }
        System.out.println(s+"的十进制形式为："+n);
    }
    //4.字节数组转换16进制
    public static String byteToHex(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex=Integer.toHexString(bytes[i]);
            if(hex.length()<2)
                hex="0"+hex;
            sb.append(hex);
        }
        return sb.toString();
    }
    //5.16进制转换字节 1byte（8bit 每4bit16种情况 8bit用两个16进制数表示)=2Hex
    public static byte hexToByte(String hex){
        return (byte) Integer.parseInt(hex,16);
    }
    //6.16进制转换字节数组
    public static byte[] hexToBytes(String hex){
        int length=hex.length();
        byte[] result;
        if(length%2!=0){
            result=new byte[(length+1)/2];
            hex="0"+hex;
        }else {
            result=new byte[length/2];
        }
        int j=0;
        for(int i=0;i<hex.length();i+=2){
            result[j]=hexToByte(hex.substring(i,i+2));
            j++;
        }
        return result;
    }
}
