/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berlinclock;

import java.awt.Component;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JPanel;
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
public class ClockTest
{

    public ClockTest()
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

    /**
     * Test of main method, of class Clock.
     */
    @Test
    public void testMain()
    {
        System.out.println("main");
        String[] args = null;
        Clock.main(args);

        assertEquals(new Clock(1.0, TimeZone.getDefault(), true).getBounds(), new Clock().getBounds());
        assertEquals(new Clock(1.0).getBounds(), new Clock().getBounds());
        assertEquals(new Clock(1.0).getBounds(), new Clock(1.0, TimeZone.getDefault(), true).getBounds());

        assertNotEquals(new Clock(2.0, TimeZone.getDefault(), true).getBounds(), new Clock().getBounds());
        
        assertTrue(new Clock(1.0, TimeZone.getTimeZone("GMT-8:00"), true).isDisplayable());
        assertTrue(new Clock().isDisplayable());
        assertTrue(new Clock(1.0).isDisplayable());
        assertTrue(new Clock(1.0,TimeZone.getDefault(), true).getContentPane().getComponents()[1] instanceof JButton);

        assertFalse(new Clock(70.0,TimeZone.getDefault(), false).isDisplayable());
        assertFalse(new Clock(0.0,TimeZone.getDefault(), false).isDisplayable());
        assertEquals(new Clock(1.0).convertTime("10:24:50"), "\nYY--\n" +"----\n" +"YYRY-------\n" +"YYYY");
        assertEquals(new Clock(1.0).convertTime("20:24:50"), "\nYYYY\n" +"----\n" +"YYRY-------\n" +"YYYY");
        assertEquals(new Clock(1.0).convertTime("blablabla"),"");
        assertEquals(new Clock(1.0).convertTime("30:24:50"),"");
        assertEquals(new Clock(1.0).convertTime("10:64:50"),"");
        assertNotEquals(new Clock(1.0).convertTime("10:24"),"");
    }

}
