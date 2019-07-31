/**
 *
 */
public class Planet {
    public double xxPos; //Its current x position
    public double yyPos; //Its current y position
    public double xxVel; //Its current velocity in the x direction
    public double yyVel; //Its current velocity in the y direction
    public double mass;  //Its mass
    public String imgFileName; //The name of the file that corresponds to the image that depicts the planet (for example, jupiter.gif)
    private static final double g = 6.67e-11; //Java supports scientific notation

    /**
     * The first constructor initialize an instance of the Planet class
     */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /**
     * The second constructor should take in a Planet object and initialize an identical Planet object (i.e. a copy).
     *
     * @param p a Planet
     */
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * calculates the distance between two Planets
     *
     * @param p a Planet
     * @return double distance between two Planets
     */
    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);  //Math.pow will result in slower code
    }

    /**
     * takes in a planet, and returns a double describing the force exerted on this planet by the given planet
     *
     * @param p a Planet
     */
    public double calcForceExertedBy(Planet p) {
        double force = g * this.mass * p.mass / Math.pow(calcDistance(p), 2);
        return force;
    }

    /**
     * describe the force exerted in the X direction
     */
    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - this.xxPos;
        return calcForceExertedBy(p) * dx / calcDistance(p);
    }

    /**
     * describe the force exerted in the Y direction
     */
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - this.yyPos;
        return calcForceExertedBy(p) * dy / calcDistance(p);
    }

    /**
     * take in an array of Planets and calculate the net X force
     * exerted by all planets in that array upon the current Planet.
     * @param pArray may contains the target planet.
     */
    public double calcNetForceExertedByX(Planet[] pArray) {
        double netForceX = 0;
        for (Planet p : pArray) {
            if (this.equals(p)){    //equals vs == : == 比较value；equals default比较存储位置，可被改写
                continue;
            }
            netForceX += calcForceExertedByX(p);
        }
        return netForceX;
    }

    /**
     * take in an array of Planets and calculate the net Y force
     * exerted by all planets in that array upon the current Planet.
     * @param pArray may contains the target planet.
     */
    public double calcNetForceExertedByY(Planet[] pArray) {
        double netForceY = 0;
        for (Planet p : pArray) {
            if (this.equals(p)){
                continue;
            }
            netForceY += calcForceExertedByY(p);
        }
        return netForceY;
    }

    /**
     * update the planet’s position and velocity instance variables
     */
    public void update(double dt, double netForceX, double netForceY) {
        double aX = netForceX/this.mass;
        double aY = netForceY/this.mass;
        this.xxVel+=aX*dt;
        this.yyVel+=aY*dt;
        this.xxPos+=xxVel*dt;
        this.yyPos+=yyVel*dt;
    }

    public void draw(){
        /*  */
        StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}
