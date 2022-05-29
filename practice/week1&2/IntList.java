
public class IntList {
	public int first;
	public IntList rest;

	public IntList(int f, IntList r) {
		first = f;
		rest = r;
	}
	public static IntList incrList(IntList L, int x) {
        /* Your code here. */
        int i=1;
        int L_size = L.size();
        IntList c_new = new IntList(L.get(L_size-i)+x,null);
        while (i<L_size) {
        	c_new = new IntList(L.get(L_size-1-i)+x,c_new);
        	i++;
        }        
        return c_new;
    }

    /** Returns an IntList identical to L, but with
      * each element incremented by x. Not allowed to use
      * the 'new' keyword. */
    public static IntList dincrList(IntList L, int x) {
    	dincrListhelper(L,x);
    	return L;
    }
    public static void dincrListhelper(IntList L,int x){
    	if(L==null){
    		return;
    	}else{
    		L.first+=x;
    		dincrListhelper(L.rest,x);
    	}
    }

	/** Return the size of the list using... recursion! */
	public int size() {
		return sizehelper(this,0);
	}
	public int sizehelper(IntList L, int i){
		if(L==null){
			return i;
		}else{
			return sizehelper(L.rest , i+1);
		}
	}

	/** Return the size of the list using no recursion! */
	public int iterativeSize() {
		int i=0;
		IntList copy=this;
		while(copy!=null){
			i++;
			copy = copy.rest;
		} return i;
	}

	public static void printList(IntList L){
		if (L==null) {
			System.out.println("null");
			return;
		} else{
		System.out.print(L.first+"->");
		printList(L.rest);
	}
	}

	/** Returns the ith value in this list.*/
	public int get(int i) {
		IntList copy = this;
		while(i!=0){
			i=i-1;
			copy=copy.rest;
		}return copy.first;
	}

	public static void main(String[] args) {
        IntList L = new IntList(5, null);
        L.rest = new IntList(7, null);
        L.rest.rest = new IntList(9, null);
        System.out.println(L.size());
        System.out.println(L.iterativeSize());
		System.out.println(L.get(1));
        printList(incrList(L, 3));
        printList(L);        
        printList(dincrList(L, 3));
	}
} 
