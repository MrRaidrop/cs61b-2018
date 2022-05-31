
public class IntList {
	public int first;
	public IntList rest;

	public IntList(int f, IntList r) {
		first = f;
		rest = r;
	}
	//return a IntList that added x to every element of IntList L without changing L
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

  /*If 2 numbers in a row are the same, we add them together
  * and make one large node */
  public void addAdjacent(){
    	if(this.rest==null) {
    		return;
    	}if(this.first==this.rest.first){
    		this.first = this.first*2;
    		this.rest = this.rest.rest;
    		this.addAdjacent();
    	}else {return;
    	}
  }

  //add x to the ass of L.
  public static IntList addLast(IntList L,int x){
  	int L_len = L.size();
  	IntList copy = L;
  	while(copy != null){
  		if(copy.rest == null){
  			copy.rest = new IntList(x , null);
  			break;
  		}else{
  			copy=copy.rest;
  		}
  	}
  	return copy;
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

	//print the List out~~
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

	/*add x to the ass of L and add the squre of elements after every ele.*/
	public void addsqure(int x){
		IntList copy = this;
		IntList senitel = new IntList(12,null);
		while(copy!=null){
			addLast(senitel,copy.first);
			addLast(senitel,copy.first*copy.first);
			copy=copy.rest;
		}
		addLast(senitel,x);
		this.rest = senitel.rest.rest;
	}

	public static void main(String[] args) {
        IntList L = new IntList(1, null);
        L.rest = new IntList(1, null);
        L.rest.rest = new IntList(5, null);
        L.rest.rest.rest = new IntList(3, null);
        System.out.println(L.size());
        System.out.println(L.iterativeSize());
				System.out.println(L.get(1));
        printList(incrList(L, 3));//doesn't change L
        printList(L);        
        printList(dincrList(L, 3));//add 3 to every ele in L
        printList(L);
        L.addAdjacent(); //merge the same in row and make it double
        printList(L);
        addLast(L,3); //add 3 to the ass of the List
        printList(L);
        L.addsqure(100);//add the square of each ele in L and add 100 to the ass
        printList(L);
	}
} 
