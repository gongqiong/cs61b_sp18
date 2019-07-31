public class NBody {
    /**
     * Given a file name, it should return a double corresponding to the radius of the universe in that file
     */
    public static double readRadius(String name) {
        In in = new In(name);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /**
     * Given a file name, it should return an array of Planets corresponding to the planets in the file
     */
    public static Planet[] readPlanets(String name) {
        In in = new In(name);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] pArray = new Planet[num];
        double xxPos;
        double yyPos;
        double xxVel;
        double yyVel;
        double mass;
        String imgFileName;
        for (int i = 0; i < num; i += 1) {
            xxPos = in.readDouble();
            yyPos = in.readDouble();
            xxVel = in.readDouble();
            yyVel = in.readDouble();
            mass = in.readDouble();
            imgFileName = in.readString();
            pArray[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return pArray;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);

        /** Sets up the universe so that it matches the radius of the universe */
        StdDraw.setScale(-radius, radius);

        /* Clears the drawing window. */
        StdDraw.clear();

        /**
         * Then draw the image starfield.jpg as the background.
         */
        StdDraw.picture(0,0,"images/starfield.jpg");

        Planet[] pArray = readPlanets(filename);
        for (Planet p: pArray){
            StdDraw.picture(p.xxPos,p.yyPos,"images/"+p.imgFileName);
        }

        /**
         * Enable double buffering by calling enableDoubleBuffering()
         */
        StdDraw.enableDoubleBuffering();


        for (double t = 0; t<=T; t+=dt){
            double[] xForces = new double[pArray.length];
            double[] yForces = new double[pArray.length];
            for (int i =0; i< pArray.length; i+=1){
                double netForceX= pArray[i].calcNetForceExertedByX(pArray);
                double netForceY= pArray[i].calcNetForceExertedByY(pArray);
                xForces[i]=netForceX;
                yForces[i]=netForceY;
            }
            for (int i =0; i< pArray.length; i+=1){
                pArray[i].update(dt, xForces[i],yForces[i]);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");

            for (Planet p: pArray){
                StdDraw.picture(p.xxPos,p.yyPos,"images/"+p.imgFileName);
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", pArray.length);
        StdOut.printf("%.2e\n", radius);
        for (Planet p: pArray){
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    p.xxPos, p.yyPos, p.xxVel,
                    p.yyVel, p.mass, p.imgFileName);
        }
    }
}
