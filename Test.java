import java.util.Random;
import java.util.ArrayList;

public class Test{
	public static void main(String[] args) {
		ArrayList<String> test = new ArrayList<>();
		printElements(test);
		test.clear();
		printElements(test);
	}

	public static void removeElements(ArrayList<String> array){
		for(int i = array.size() - 1; i >=0; i--){
			array.remove(i);
		}
	}
	public static void printElements(ArrayList<String> array){
		for(int i = 0; i < array.size(); i++){
			System.out.println(array.get(i));
		}
		System.out.println("Elements printed");
	}
}