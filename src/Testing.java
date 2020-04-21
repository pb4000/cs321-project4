public class Testing {
    public static void main(String[] args) {
        String temp = "Hello";
        temp += "\nworld!";
        System.out.println(temp);

        Long[] list = new Long[1];
        list[0] = 0b010001L;
        System.out.println(Long.toBinaryString(list[0]));
    }
}