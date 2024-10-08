import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuadTreeNodeImplTest {
    private int[][] nullImage;
    private int[][] emptyImage1;
    private int[][] emptyImage2;
    private int[][] nonPower2Image1;
    private int[][] nonPower2Image2;
    private int[][] nonSquareImage;
    private int[][] testImage1;
    private int[][] modifiedTestImage1;
    private int[][] testImage2;
    private int[][] modifiedTestImage2;
    private int[][] testImage3;
    private int[][] modifiedTestImage3;
    private int[][] testImage4;
    private int[][] modifiedTestImage4;
    private int[][] testImage5;
    private int[][] modifiedTestImage5;
    private int[][] testImage6;
    private int[][] modifiedTestImage6;

    @Before
    public void setupTestImages() {
        nullImage = null;

        emptyImage1 = new int[0][0];
        emptyImage2 = new int[1][0];

        nonPower2Image1 = new int[4][3];
        nonPower2Image2 = new int[3][4];

        nonSquareImage = new int[][]{
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1}
        };

        testImage1 = new int[][]{
                {1, 1, 2, 2},
                {1, 1, 1, 2},
                {-1, 1, 0, 0},
                {1, 2, 0, 0}
        };

        modifiedTestImage1 = new int[][]{
                {0, 1, 2, 2},
                {1, 1, 1, 2},
                {-1, 1, 0, 0},
                {1, 2, 0, 0}
        };

        testImage2 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        modifiedTestImage2 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        testImage3 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        modifiedTestImage3 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        testImage4 = new int[][]{
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        modifiedTestImage4 = new int[][]{
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
        };

        testImage5 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
        };

        modifiedTestImage5 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };

        testImage6 = new int[][]{
                {0, -1, -1, -1, -1, -1, -1, 0},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
        };

        modifiedTestImage6 = new int[][]{
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
        };


    }

    @Test
    public void testNullImageException() {
        boolean thrown = false;
        try {
            QuadTreeNodeImpl.buildFromIntArray(nullImage);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testEmptyImageException() {
        boolean thrown1 = false;
        boolean thrown2 = false;
        try {
            QuadTreeNodeImpl.buildFromIntArray(emptyImage1);
        } catch (IllegalArgumentException e) {
            thrown1 = true;
        }
        try {
            QuadTreeNodeImpl.buildFromIntArray(emptyImage2);
        } catch (IllegalArgumentException e) {
            thrown2 = true;
        }
        assertTrue(thrown1 && thrown2);
    }

    @Test
    public void testNonPower2Exception() {
        boolean thrown1 = false;
        boolean thrown2 = false;
        try {
            QuadTreeNodeImpl.buildFromIntArray(nonPower2Image1);
        } catch (IllegalArgumentException e) {
            thrown1 = true;
        }
        try {
            QuadTreeNodeImpl.buildFromIntArray(nonPower2Image2);
        } catch (IllegalArgumentException e) {
            thrown2 = true;
        }
        assertTrue(thrown1 && thrown2);
    }

    @Test
    public void testNonSquareException() {
        boolean thrown = false;
        try {
            QuadTreeNodeImpl.buildFromIntArray(nonSquareImage);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testImage1() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage1);
        assertEquals(1, root.quad1.color);
        assertEquals(2, root.quad2.quad2.color);
        assertEquals(13, root.getSize());
        assertEquals(4, root.getDimension());
        assertEquals(0.8125, root.getCompressionRatio(), 0);
        assertTrue(root.quad1.isLeaf());
        assertFalse(root.quad2.isLeaf());
        assertEquals(root.quad2, root.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT));

        assertArrayEquals(testImage1, root.decompress());
        assertEquals(2, root.getColor(2, 0));
        assertEquals(0, root.getColor(2, 2));
        assertEquals(2, root.getColor(3, 0));

        boolean thrown = false;
        try {
            root.getColor(-1, 4);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        root.setColor(0, 0, 0);
        assertArrayEquals(modifiedTestImage1, root.decompress());
    }

    @Test
    public void testImage2() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage2);
        root.setColor(7, 0, 2);
        assertArrayEquals(modifiedTestImage2, root.decompress());
    }

    @Test
    public void testImage3() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage3);
        root.setColor(7, 0, 1);
        assertArrayEquals(modifiedTestImage3, root.decompress());
    }

    @Test
    public void testImage4() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage4);
        root.setColor(7, 0, 1);
        assertArrayEquals(modifiedTestImage4, root.decompress());
    }

    @Test
    public void testImage5() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage5);
        root.setColor(1, 1, 0);
        root.setColor(2, 5, 0);
        assertFalse(root.isLeaf());
        root.setColor(7, 7, 0);
        assertTrue(root.isLeaf());
        assertArrayEquals(modifiedTestImage5, root.decompress());
    }

    @Test
    public void testImage6() {
        QuadTreeNodeImpl root = QuadTreeNodeImpl.buildFromIntArray(testImage6);
        assertFalse(root.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        root.setColor(0, 0, -1);
        assertTrue(root.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        assertFalse(root.isLeaf());
        assertFalse(root.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).isLeaf());
        root.setColor(7, 0, -1);
        assertArrayEquals(modifiedTestImage6, root.decompress());
        assertTrue(root.isLeaf());
    }
}
