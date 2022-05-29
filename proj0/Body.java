/**
 * Body
 *
 * @author GreysonYu
 * @create 2022-05-28-20:01
 **/
public class Body {
    /** create the bodies and forces between them*/
    final static double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV,
    			  double yV, double m, String img){
        this.xxPos=xP;
        this.yyPos=yP;
        this.xxVel=xV;
        this.yyVel=yV;
        this.mass=m;
        this.imgFileName=img;
    }

    public Body(Body b){
        this.xxPos=b.xxPos;
        this.yyPos=b.yyPos;
        this.xxVel=b.xxVel;
        this.yyVel=b.yyVel;
        this.mass=b.mass;
        this.imgFileName=b.imgFileName;
    }

    /** take in a Body b and calculate the distance between Body this and b. */
    public double calcDistance(Body b){
        double Fx = this.xxPos - b.xxPos;
        double Fy = this.yyPos - b.yyPos;
        double distance = Math.sqrt(Fx*Fx+Fy*Fy);
        return distance;  
    }

    /** take in a Body b and calculate the force between Body this and b. */
    public double calcForceExertedBy(Body b){
        double r = this.calcDistance(b);
        double F = G * this.mass * b.mass / ( r * r );
        return F;
    }

    /** take in a Body b and calculate the force in X direction
     *  between Body this and b. */
    public double calcForceExertedByX(Body b){
        double r = this.calcDistance(b);
        double F = this.calcForceExertedBy(b);
        double Fx = F * (b.xxPos - this.xxPos) / r ;
        return Fx;
     }

    /** take in a Body b and calculate the force in Y direction
     *  between Body this and b. */
     public double calcForceExertedByY(Body b){
        double r = this.calcDistance(b);
        double F = this.calcForceExertedBy(b);
        double Fy = F * (b.yyPos - this.yyPos) / r ;
        return Fy;
     }

    /** take in  Body[] and calculate the forces in X direction
     *  between Body this and bodys. */
    public double calcNetForceExertedByX(Body[] allbodys){
        double NetFx = 0;
        for(Body b : allbodys){
            if (b.equals(this)){
                continue;
            }else{
                NetFx = NetFx + this.calcForceExertedByX(b);
            }
        }
        return NetFx;
     }

    /** take in  Body[] and calculate the forces in Y direction
     *  between Body this and bodys. */
    public double calcNetForceExertedByY(Body[] allbodys){
        double NetFy = 0;
        for(Body b : allbodys){
            if (b.equals(this)){
                continue;
            }else{
                NetFy = NetFy + this.calcForceExertedByY(b);
            }
        }
        return NetFy;
     }

     /** take in a time dt, force in X direction Fx, force in Y direction Fy
      * and update the current velocity and position of this. */
    public void update(double dt, double Fx, double Fy){
        double aX = Fx / this.mass;
        double aY = Fy / this.mass;
        this.xxVel = this.xxVel + dt * aX;
        this.yyVel = this.yyVel + dt * aY;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    /** draw a body with StdDraw.*/
    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"images/"+imgFileName);
    }

}
