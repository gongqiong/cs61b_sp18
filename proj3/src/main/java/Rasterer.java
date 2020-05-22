import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // private double LonDPP;
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    /**
     * Each tile is 256x256 pixels.
     */
    public static final int TILE_SIZE = 256;
    
    public Rasterer() {
        // YOUR CODE HERE
    }
    
    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double queryUllon = params.get("ullon");
        double queryLrlon = params.get("lrlon");
        double queryUllat = params.get("ullat");
        double queryLrlat = params.get("lrlat");
        double queryWidth = params.get("w");
        boolean querySuccess = true;
        if (queryUllon > queryLrlon || queryUllat < queryLrlat
                || queryUllon>ROOT_LRLON || queryLrlon<ROOT_ULLON
                || queryUllat<ROOT_LRLAT || queryLrlat>ROOT_ULLAT){
            querySuccess = false;
        }
        results.put("query_success", querySuccess);
        
        if (!querySuccess){
            return results;
        }
        
        // find the correct depth for the query
        double queryLonDPP = (queryLrlon - queryUllon) / queryWidth;
        int depth = calcOptimalDepth(queryLonDPP);
        results.put("depth", depth);
        
        //Figure out how many tiles you’ll need.
        int numInDepth = (int) Math.pow(2, depth);
        double tileLonSize = Math.abs(ROOT_LRLON - ROOT_ULLON) / numInDepth;
        double tileLatSize = Math.abs(ROOT_ULLAT - ROOT_LRLAT) / numInDepth;
        // the top left x position of top left tile.
        int ulTileXPos = getRasteredPos(numInDepth, tileLonSize, queryUllon, ROOT_ULLON);
        // the top left y position of top left tile.
        int ulTileYPos = getRasteredPos(numInDepth, tileLatSize, queryUllat, ROOT_ULLAT);
        // the top left x position of bottom right tile.
        int lrTileXPos = getRasteredPos(numInDepth, tileLonSize, queryLrlon, ROOT_ULLON);
        // the top left y position of bottom right tile.
        int lrTileYPos = getRasteredPos(numInDepth, tileLatSize, queryLrlat, ROOT_ULLAT);
        int yNum = lrTileYPos - ulTileYPos + 1;
        int xNum = lrTileXPos - ulTileXPos + 1;
        String[][] render_grid = new String[yNum][xNum];
        int xPos = ulTileXPos;
        int yPos = ulTileYPos;
        for (int y = 0; y < yNum; y += 1) {
            xPos = ulTileXPos;
            for (int x = 0; x < xNum; x += 1) {
                render_grid[y][x] = "d" + depth + "_x" + xPos + "_y" + yPos + ".png";
                xPos += 1;
            }
            yPos += 1;
        }
        results.put("render_grid", render_grid);
        Map<String, Double> ulBox = getBoundingBox(tileLonSize, tileLatSize, ulTileXPos, ulTileYPos);
        Map<String, Double> lrBox = getBoundingBox(tileLonSize, tileLatSize, lrTileXPos, lrTileYPos);
        double raster_ul_lon = ulBox.get("tile_ullon");
        results.put("raster_ul_lon", raster_ul_lon);
        double raster_ul_lat = ulBox.get("tile_ullat");
        results.put("raster_ul_lat", raster_ul_lat);
        double raster_lr_lon = lrBox.get("tile_lrlon");
        results.put("raster_lr_lon", raster_lr_lon);
        double raster_lr_lat = lrBox.get("tile_lrlat");
        results.put("raster_lr_lat", raster_lr_lat);
        return results;
    }
    
    private int getRasteredPos(int numInDepth, double tileSize, double query, double root) {
        int pos = (int) Math.floor(Math.abs(query - root) / tileSize);
        if (pos < 0) {
            pos = 0;
        } else if (pos > numInDepth - 1) {
            pos = numInDepth - 1;
        }
        return pos;
    }
    
    //Figure out how to compute the bounding box for a given filename.
    private Map<String, Double> getBoundingBox(double tileLonSize, double tileLatSize,
                                               int xPos, int yPos) {
        double tile_ullon = ROOT_ULLON + xPos * tileLonSize;
        double tile_ullat = ROOT_ULLAT - yPos * tileLatSize;
        double tile_lrlon = tile_ullon + tileLonSize;
        double tile_lrlat = tile_ullat - tileLatSize;
        Map<String, Double> boundingBox = new HashMap<>();
        boundingBox.put("tile_ullon", tile_ullon);
        boundingBox.put("tile_ullat", tile_ullat);
        boundingBox.put("tile_lrlon", tile_lrlon);
        boundingBox.put("tile_lrlat", tile_lrlat);
        return boundingBox;
    }
    
    private int calcOptimalDepth(double queryLonDPP) {
        double lonDPP = (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;
        int depth = (int) Math.ceil(Math.log(lonDPP / queryLonDPP) / Math.log(2));
        if (depth > 7) {
            depth = 7;
        }
        return depth;
    }
}
