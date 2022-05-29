/**
 * Nbody
 *
 * @author GreysonYu
 * @create 2022-05-29-16:15
 **/
public class NBody{

	/** take in a String of filename return the second double number which
	 * is the radius of the universe.*/
	public static double readRadius(String filename){
		In in = new In(filename);
		in.readDouble();
		double Radius_universe = in.readDouble();
		return Radius_universe;
	}

	/** take in a String of filename return a array of Bodys which
	 * staus are the third double number and the afters
	 * of the file which are the status of the bodies.*/
	public static Body[] readBodies(String filename){
		In in = new In(filename);
		int number_bodies = in.readInt();
		in.readDouble();
		Body[] theBodies = new Body [number_bodies];
		int i = 0;
		while (i<number_bodies){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Body b = new Body(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
			theBodies[i] = b;
			i += 1;
		}
		return theBodies;
	}

	public static void main(String[] args) {
		//take in the status
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double Radius_universe = readRadius(filename);
		Body[] theBodies = readBodies(filename);
		int number_bodies = theBodies.length;
		//draw the background
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-Radius_universe, Radius_universe);
		StdDraw.clear();
		StdDraw.picture(0,0,"images/starfield.jpg");
		for(Body b : theBodies){
			b.draw();
		}
		double current_time = 0;
		while(current_time<T){
			//calc the forces
			double[] xForces = new double[number_bodies];
			double[] yForces = new double[number_bodies];
			for(int i=0;i<number_bodies;i++){
				xForces[i] = theBodies[i].calcNetForceExertedByX(theBodies);
	        	yForces[i] = theBodies[i].calcNetForceExertedByY(theBodies);
			}
			StdDraw.picture(0,0,"images/starfield.jpg");
			//update the bodies and draw them
			for(int j=0;j<number_bodies;j++){
				theBodies[j].update(dt,xForces[j], yForces[j]);
				theBodies[j].draw();
			}
			StdDraw.show();
	        StdDraw.pause(10);
	        current_time = dt + current_time;
    	}
    	//print the last status
    	StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  		bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  		bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
}
	}
} 