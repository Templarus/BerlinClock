/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berlinclock;

import javax.swing.JLabel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TemplaRus
 */
public class BlockTest
{

    public BlockTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testSomeMethod()
    {
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(new Block(true, 1.0).isEnabled());
        assertTrue(new Block(false, 5).isEnabled());
        assertTrue(new Block(true, 0.7, 10, 50, -1).isEnabled());
        
        assertFalse(new Block(false, 111, -100).isEnabled());
        assertFalse(new Block(true, 0.5, 0, -100, 1234).isEnabled());
        assertFalse(new Block(true, 1, -10, -100, 1234).isEnabled());
        assertFalse(new Block(true, 1.123, -10, -100, -1234).isEnabled());
        assertFalse(new Block(true, 6, 10, -100, -1).isEnabled());

        assertFalse(new Block(false, 111, -100).isDisplayable());
        assertFalse(new Block(true, 0.5, 0, -100, 1234).isDisplayable());
        assertFalse(new Block(true, 1, -10, -100, 1234).isDisplayable());
        assertFalse(new Block(true, 1.123, -10, -100, -1234).isDisplayable());
        assertFalse(new Block(true, 6, 10, -100, -1).isDisplayable());

        assertArrayEquals(new Block(false, 1.0, 5).getComponents(), new Block(false, 1.0, 40, 80, 5).getComponents());
        assertEquals(new Block(false, 1.0, 5).getSize(), new Block(false, 1.0, 40, 80, 5).getSize());
        assertEquals(new Block(true, 15, 16).getBackground(), new Block(true, 15, 40, 80, 13).getBackground());
        assertNotEquals(new Block(true, 0.5, 17).getBackground(), new Block(true, 0.5, 40, 80, 13).getBackground());
        assertNotEquals(new Block(false, 10, 16).getBackground(), new Block(true, 10, 40, 80, 13).getBackground());
        for (int i = 0; i < 23; i++)
        {
            assertEquals(new Block(true, i, i).getBackground(), new Block(true, i, 40, 80, i).getBackground());
            assertEquals(new Block(false, i, i).getSize(), new Block(false, i, 40, 80, i).getSize());
        }
    }

}
