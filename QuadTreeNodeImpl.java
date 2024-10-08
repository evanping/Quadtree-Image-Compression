public class QuadTreeNodeImpl implements QuadTreeNode {

    QuadTreeNodeImpl quad1 = null;
    QuadTreeNodeImpl quad2 = null;
    QuadTreeNodeImpl quad3 = null;
    QuadTreeNodeImpl quad4 = null;

    int color;
    boolean leaf = false;
    int rmin = -1, rmax = -1, cmin = -1, cmax = -1;

    /**
     * Constructors
     */
    public QuadTreeNodeImpl(QuadTreeNodeImpl quad1, QuadTreeNodeImpl quad2,
                            QuadTreeNodeImpl quad3, QuadTreeNodeImpl quad4) {
        this.quad1 = quad1;
        this.quad2 = quad2;
        this.quad3 = quad3;
        this.quad4 = quad4;
    }

    public QuadTreeNodeImpl(int color) {
        this.color = color;
        this.leaf = true;
    }

    private void setBounds(int rmin, int rmax, int cmin, int cmax) {
        this.rmin = rmin;
        this.rmax = rmax;
        this.cmin = cmin;
        this.cmax = cmax;
    }

    /**
     * @param image image to put into the tree
     * @return the newly build QuadTreeNode instance which stores the compressed image
     * @throws IllegalArgumentException if image is null
     * @throws IllegalArgumentException if image is empty
     * @throws IllegalArgumentException if image.length is not a power of 2
     * @throws IllegalArgumentException if image, the 2d-array, is not a perfect square
     */

    public static QuadTreeNodeImpl buildFromIntArray(int[][] image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        } else if (image.length == 0 || image[0].length == 0) {
            throw new IllegalArgumentException("image is empty");
        } else if ((image.length & (image.length - 1)) != 0
                || (image[0].length & (image[0].length - 1)) != 0) {
            throw new IllegalArgumentException("image.length is not a power of 2");
        } else {
            for (int i = 0; i < image.length; i++) {
                if (image[i].length != image.length) {
                    throw new IllegalArgumentException("image is not a perfect square");
                }
            }
        }

        QuadTreeNodeImpl root = buildFromIntArray(image, 0, image.length, 0, image[0].length, 0, 0);
        return root;
    }

    /**
     * @param image image to put into the tree
     * @param rmin lower bound for row index, inclusive
     * @param rmax upper bound for row index, exclusive
     * @param cmin lower bound for col index, inclusive
     * @param cmax upper bound for col index, exclusive
     * @param rbuffer added rows for setColor
     * @param cbuffer added cols for setColor
     */
    public static QuadTreeNodeImpl buildFromIntArray(int[][] image, int rmin,
                                                     int rmax, int cmin, int cmax,
                                                     int rbuffer, int cbuffer) {
        QuadTreeNodeImpl root;
        // return color node if dimension is 1x1
        if (rmax - rmin == 1 && cmax - cmin == 1) {
            root = new QuadTreeNodeImpl(image[rmin][cmin]);
            root.setBounds(rmin + rbuffer, rmax + rbuffer, cmin + cbuffer, cmax + cbuffer);
            return root;
        }

        // return color node if all one color
        boolean solid = true;
        int firstColor = image[rmin][cmin];
        for (int r = rmin; r < rmax; r++) {
            for (int c = cmin; c < cmax; c++) {
                if (image[r][c] != firstColor) {
                    solid = false;
                    break;
                }
            }
        }
        if (solid) {
            root = new QuadTreeNodeImpl(firstColor);
            root.setBounds(rmin + rbuffer, rmax + rbuffer, cmin + cbuffer, cmax + cbuffer);
            return root;
        }

        // Divide into quadrants and return quad node
        int rmid = (rmax + rmin) / 2, cmid = (cmax + cmin) / 2;
        // Quadrant 1 (top left)
        QuadTreeNodeImpl quad1 = buildFromIntArray(
                image, rmin, rmid, cmin, cmid, rbuffer, cbuffer);
        quad1.setBounds(rmin + rbuffer, rmid + rbuffer, cmin + cbuffer, cmid + cbuffer);
        // Quadrant 2 (top right)
        QuadTreeNodeImpl quad2 = buildFromIntArray(
                image, rmin, rmid, cmid, cmax, rbuffer, cbuffer);
        quad2.setBounds(rmin + rbuffer, rmid + rbuffer, cmid + cbuffer, cmax + cbuffer);
        // Quadrant 3 (bottom left)
        QuadTreeNodeImpl quad3 = buildFromIntArray(
                image, rmid, rmax, cmin, cmid, rbuffer, cbuffer);
        quad3.setBounds(rmid + rbuffer, rmax + rbuffer, cmin + cbuffer, cmid + cbuffer);
        // Quadrant 4 (bottom right)
        QuadTreeNodeImpl quad4 = buildFromIntArray(
                image, rmid, rmax, cmid, cmax, rbuffer, cbuffer);
        quad4.setBounds(rmid + rbuffer, rmax + rbuffer, cmid + cbuffer, cmax + cbuffer);

        root = new QuadTreeNodeImpl(quad1, quad2, quad3, quad4);
        root.setBounds(rmin + rbuffer, rmax + rbuffer, cmin + cbuffer, cmax + cbuffer);
        return root;
    }

    @Override
    public int getColor(int x, int y) {
        int rely = y + rmin;
        int relx = x + cmin;
        if (rely >= rmax || rely < rmin || relx >= cmax || relx < cmin) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        return getColorHelper(x, y);
    }

    private int getColorHelper(int x, int y) {
        if (leaf) {
            return color;
        }

        int rmid = (rmax + rmin) / 2, cmid = (cmax + cmin) / 2;

        if (y < rmid && x < cmid) {
            return quad1.getColorHelper(x, y);
        } else if (y < rmid && x >= cmid) {
            return quad2.getColorHelper(x, y);
        } else if (y >= rmid && x < cmid) {
            return quad3.getColorHelper(x, y);
        } else if (y >= rmid && x >= cmid) {
            return quad4.getColorHelper(x, y);
        }

        return 0;
    }

    @Override
    public void setColor(int x, int y, int c) {
        if (y >= rmax || y < rmin || x >= cmax || x < cmin) {
            throw new IllegalArgumentException("x or y is out of bounds");
        } else if (!leaf) {
            int rmid = (rmax + rmin) / 2, cmid = (cmax + cmin) / 2;

            if (y < rmid && x < cmid) {
                quad1.setColor(x, y, c);
            } else if (y < rmid && x >= cmid) {
                quad2.setColor(x, y, c);
            } else if (y >= rmid && x < cmid) {
                quad3.setColor(x, y, c);
            } else if (y >= rmid && x >= cmid) {
                quad4.setColor(x, y, c);
            }

            if (quad1.isLeaf() && quad2.isLeaf() && quad3.isLeaf() && quad4.isLeaf()
                    && quad1.color == quad2.color && quad1.color == quad3.color
                    && quad1.color == quad4.color) {
                color = quad1.color;
                leaf = true;
                quad1 = null;
                quad2 = null;
                quad3 = null;
                quad4 = null;
            }
            return;
        }
        // leaf
        if (rmax - rmin != 1 && color != c) {
            int[][] subImage = new int[rmax - rmin][cmax - cmin];
            for (int row = 0; row < rmax - rmin; row++) {
                for (int col = 0; col < cmax - cmin; col++) {
                    if (row == y - rmin && col == x - cmin) {
                        subImage[row][col] = c;
                    } else {
                        subImage[row][col] = color;
                    }
                }
            }
            QuadTreeNodeImpl newNode = buildFromIntArray(subImage, 0,
                    subImage.length, 0, subImage[0].length, rmin, cmin);
            this.quad1 = newNode.quad1;
            this.quad2 = newNode.quad2;
            this.quad3 = newNode.quad3;
            this.quad4 = newNode.quad4;
            leaf = false;
        } else {
            color = c;
            leaf = true;
        }

    }

    @Override
    public QuadTreeNode getQuadrant(QuadName quadrant) {
        if (leaf) {
            return null;
        }

        if (quadrant == QuadName.TOP_LEFT) {
            return quad1;
        } else if (quadrant == QuadName.TOP_RIGHT) {
            return quad2;
        } else if (quadrant == QuadName.BOTTOM_LEFT) {
            return quad3;
        } else if (quadrant == QuadName.BOTTOM_RIGHT) {
            return quad4;
        }
        return null;
    }

    @Override
    public int getDimension() {
        return rmax - rmin;
    }

    @Override
    public int getSize() {
        if (leaf) {
            return 1;
        }
        int size = 1;
        size += quad1.getSize();
        size += quad2.getSize();
        size += quad3.getSize();
        size += quad4.getSize();
        return size;
    }

    @Override
    public boolean isLeaf() {
        return leaf;
    }

    @Override
    public int[][] decompress() {
        int[][] image = new int[rmax - rmin][cmax - cmin];
        this.decompressHelper(image);
        return image;
    }

    private void decompressHelper(int[][] image) {
        // if colored quadrant then fill and return
        if (leaf) {
            for (int r = rmin; r < rmax; r++) {
                for (int c = cmin; c < cmax; c++) {
                    image[r][c] = color;
                }
            }
            return;
        }

        quad1.decompressHelper(image);
        quad2.decompressHelper(image);
        quad3.decompressHelper(image);
        quad4.decompressHelper(image);
    }

    @Override
    public double getCompressionRatio() {
        double s = this.getSize();
        int d = this.getDimension();
        return s / (d * d);
    }
}
