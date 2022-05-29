public class TestBody {
	public static void main(String[] args){
		Body b = new Body(0,0,0,0,3e6,"yourmom");
		Body a = new Body(1.0,1.0,0,0,2e5,"yourdad");
		Body c = new Body(a);
		System.out.println(a.calcForceExertedBy(b));
		System.out.println(b.calcForceExertedBy(c));
	}
}