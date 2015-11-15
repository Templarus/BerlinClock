package berlinclock;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author TemplaRus
 */
public class Clock extends javax.swing.JFrame implements TimeConverter
{

    private JPanel block;
    private Boolean[] blockStat = new Boolean[23]; //array of block statuses ( false = gray, true= red\yellow)
    private Timer timer;
    private TimeZone tz;
    private double scalingfactor = 1.0; // scaling factor for UI
    private Boolean showControls = true; // boolean for switching on/off additional buttons/elements
    private String convertedTime = "";
    private String[] LightStringValues = new String[]{"-", "R", "Y"}; //String array of text statuses for text representation of received time

    /**
     * Creates new form Clock
     *
     * @param scalingfactor Scalingfactor , value ranged from 0.5 to 40.Default
     * value is 1.0
     * @param tz Timezone value, default = TimeZone.getDefault()
     * @param showControls set true if you want to see additional form controls
     *
     */
    public Clock(double scalingfactor, TimeZone tz, Boolean showControls)
    {
        if (scalingfactor >= 0.5 & scalingfactor <= 40.0)
        {
            this.scalingfactor = scalingfactor;
            this.showControls = showControls;
        } else
        {
            System.err.println("Scaling factor is out of bounds[0.5,40.0]:" + scalingfactor);
            return;
        }
        this.tz = tz;

        initComponents();
        setTime();
        if (timerInitialized(1000, TimeZone.getDefault()))
        {
            timer.start();
        }
        rescaling();

    }

    /**
     * Creates default new form Clock with default values 1.0, true ,
     * TimeZone.getDefault()
     *
     */
    public Clock()
    {
        this(1.0, TimeZone.getDefault(), true);
    }

    /**
     * Creates new form Clock with default TimeZone, and showControls value
     *
     * @param scalingfactor Scalingfactor , value ranged from 0.5 to 40.Default
     */
    public Clock(double scalingfactor)
    {
        this(scalingfactor, TimeZone.getDefault(), true);
    }
    
    @Override
    public String convertTime(String time)
    {
        String unfotmatedTime = calculateConvertedTime(time);
        if (!"".equals(unfotmatedTime))
        {
            String formatedTime = "\n" + unfotmatedTime.substring(0, 4) + "\n" + unfotmatedTime.substring(4, 8) + "\n" + unfotmatedTime.substring(8, 19) + "\n" + unfotmatedTime.substring(19, 23);
            return formatedTime;
        }
        return unfotmatedTime;
    }
    private void rescaling() //method used to rescale UI
    {
        if (!showControls)
        {
            this.remove(updateButt);
            this.remove(ExitButt);
            this.remove(jComboBox);
            this.remove(Jlabel);
        }
        setPreferredSize(new Dimension((int) (getPreferredSize().width * scalingfactor), (int) (getPreferredSize().height * scalingfactor)));
        setBounds(200, 200, (int) (220 / scalingfactor), (int) (400 / scalingfactor));
        for (Component comp : this.getContentPane().getComponents())
        {
            comp.setPreferredSize(new Dimension((int) (comp.getPreferredSize().width / scalingfactor), (int) (comp.getPreferredSize().height / scalingfactor)));
            comp.setBounds((int) (comp.getX() / scalingfactor), (int) (comp.getY() / scalingfactor), (int) (comp.getWidth() / scalingfactor), (int) (comp.getHeight() / scalingfactor));
            if (comp instanceof JButton)
            {
                JButton jb = (JButton) comp;
                jb.setFont(new Font("Tahoma", Font.PLAIN, (int) (jb.getFont().getSize() / scalingfactor)));
            }
            if (comp instanceof JLabel)
            {
                JLabel jl = (JLabel) comp;
                jl.setFont(new Font("Tahoma", Font.PLAIN, (int) (jl.getFont().getSize() / scalingfactor)));
            }
               if (comp instanceof JComboBox)
            {
                JComboBox jl = (JComboBox) comp;
                jl.setFont(new Font("Tahoma", Font.PLAIN, (int) (jl.getFont().getSize() / scalingfactor)));
            }
        }
        repaint();
    }

    private boolean timerInitialized(int delay, TimeZone tz) //timer initialization
    {
        if (delay > 0 & delay < 3600000)
        {
            this.timer = new Timer(delay, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    setTime();
                }
            });
            return true;
        }
        return false;
    }

    private void setTime() // method where we prepare boolean array .
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(tz);
        convertedTime = "";
        for (int i = 0; i < blockStat.length; i++)
        {
            blockStat[i] = false;
        }

        for (int i = 0; i < calendar.get(Calendar.HOUR_OF_DAY) / 5; i++)
        {
            blockStat[i] = true;
        }

        for (int i = 0; i < calendar.get(Calendar.HOUR_OF_DAY) % 5; i++)
        {
            blockStat[i + 4] = true;
        }
        for (int i = 0; i < calendar.get(Calendar.MINUTE) / 5; i++)
        {
            blockStat[i + 8] = true;
        }
        for (int i = 0; i < calendar.get(Calendar.MINUTE) % 5; i++)
        {
            blockStat[i + 19] = true;
        }
        jPanel.removeAll();
        repaint();
        drawBlocks();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void drawBlocks() //support methodfill Jpanel with configurated Blocks
    {

        for (int i = 0; i < blockStat.length; i++)
        {
            if (i < 4)
            {
                block = new Block(blockStat[i], scalingfactor);
                block.setLocation((int) (60 * i / scalingfactor), 0);
                convertedTime += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
            }

            if (i > 3 & i < 8)
            {
                block = new Block(blockStat[i], scalingfactor);
                block.setLocation((int) (60 * (i - 4) / scalingfactor), (int) (90 / scalingfactor));
                convertedTime += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
            }

            if (i > 7 & i < 19)
            {
                block = new Block(blockStat[i], scalingfactor * 2, i);
                block.setLocation((int) (20 * (i - 8) / scalingfactor), (int) (180 / scalingfactor));
                if (i == 10 | i == 13 | i == 16)
                {
                    convertedTime += LightStringValues[Math.abs(1 * blockStat[i].compareTo(false))].charAt(0);
                } else
                {
                    convertedTime += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
                }
            }
            if (i > 18)
            {
                block = new Block(blockStat[i], scalingfactor, i);
                block.setLocation((int) (60 * (i - 19) / scalingfactor), (int) (240 / scalingfactor));
                convertedTime += LightStringValues[Math.abs(-2 * blockStat[i].compareTo(false))].charAt(0);
            }
            jPanel.add(block);
        }
        Jlabel.setText(convertedTime);

    }

    private String calculateConvertedTime(String time) //method that returns converted time
    {
        String ct = "";
        if (!time.matches("[012]\\d:[012345]\\d:[012345]\\d") & !time.matches("[012]\\d:[012345]\\d"))
        {
            System.err.println("Input mismatch:"+time);
            return "";
        }
        String unitOfTime[] = time.split(":");
        int hour = Integer.parseInt(unitOfTime[0]);
        int minute = Integer.parseInt(unitOfTime[1]);
        if (hour > 24 | hour < 0 | minute > 59 | minute < 0)
        {
            return "";
        }
        for (int i = 0; i < blockStat.length; i++)
        {
            blockStat[i] = false;
        }
        for (int i = 0; i < hour / 5; i++)
        {
            blockStat[i] = true;
        }
        for (int i = 0; i < hour % 5; i++)
        {
            blockStat[i + 4] = true;
        }
        for (int i = 0; i < minute / 5; i++)
        {
            blockStat[i + 8] = true;
        }
        for (int i = 0; i < minute % 5; i++)
        {
            blockStat[i + 19] = true;
        }
        for (int i = 0; i < blockStat.length; i++)
        {
            if (i < 4)
            {
                ct += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
            }

            if (i > 3 & i < 8)
            {
                ct += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
            }

            if (i > 7 & i < 19)
            {
                if (i == 10 | i == 13 | i == 16)
                {
                    ct += LightStringValues[Math.abs(1 * blockStat[i].compareTo(false))].charAt(0);
                } else
                {
                    ct += LightStringValues[Math.abs(2 * blockStat[i].compareTo(false))].charAt(0);
                }
            }
            if (i > 18)
            {
                ct += LightStringValues[Math.abs(-2 * blockStat[i].compareTo(false))].charAt(0);
            }
        }
        return ct;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel = new javax.swing.JPanel();
        updateButt = new javax.swing.JButton();
        ExitButt = new javax.swing.JButton();
        jComboBox = new javax.swing.JComboBox();
        Jlabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame"); // NOI18N
        setUndecorated(true);
        getContentPane().setLayout(null);

        jPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel.setName("Jpanel"); // NOI18N
        jPanel.setPreferredSize(new java.awt.Dimension(220, 320));
        jPanel.setLayout(null);
        getContentPane().add(jPanel);
        jPanel.setBounds(0, 0, 220, 320);

        updateButt.setText("Manual Update");
        updateButt.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        updateButt.setMinimumSize(new java.awt.Dimension(0, 0));
        updateButt.setName("updateButt"); // NOI18N
        updateButt.setPreferredSize(new java.awt.Dimension(110, 23));
        updateButt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                updateButtActionPerformed(evt);
            }
        });
        getContentPane().add(updateButt);
        updateButt.setBounds(0, 380, 110, 23);

        ExitButt.setText("Exit");
        ExitButt.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        ExitButt.setMinimumSize(new java.awt.Dimension(0, 0));
        ExitButt.setName("ExitButt"); // NOI18N
        ExitButt.setPreferredSize(new java.awt.Dimension(110, 23));
        ExitButt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ExitButtActionPerformed(evt);
            }
        });
        getContentPane().add(ExitButt);
        ExitButt.setBounds(110, 380, 110, 23);

        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(TimeZone.getAvailableIDs()));
        jComboBox.setName("jComboBox"); // NOI18N
        jComboBox.setPreferredSize(new java.awt.Dimension(220, 20));
        jComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox);
        jComboBox.setBounds(0, 320, 220, 20);

        Jlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Jlabel.setText(convertedTime);
        Jlabel.setName("Jlabel"); // NOI18N
        Jlabel.setPreferredSize(new java.awt.Dimension(220, 14));
        getContentPane().add(Jlabel);
        Jlabel.setBounds(0, 365, 220, 14);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.0", "0.5", "2.0" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.setPreferredSize(new java.awt.Dimension(220, 20));
        jComboBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(0, 340, 220, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ExitButtActionPerformed
    {//GEN-HEADEREND:event_ExitButtActionPerformed
        dispose();
    }//GEN-LAST:event_ExitButtActionPerformed

    private void updateButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_updateButtActionPerformed
    {//GEN-HEADEREND:event_updateButtActionPerformed
        setTime();
    }//GEN-LAST:event_updateButtActionPerformed

    private void jComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxActionPerformed
    {//GEN-HEADEREND:event_jComboBoxActionPerformed
        this.tz = TimeZone.getTimeZone(ZoneId.of(jComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_jComboBoxActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox1ActionPerformed
    {//GEN-HEADEREND:event_jComboBox1ActionPerformed
        scalingfactor=Double.parseDouble(jComboBox1.getSelectedItem().toString());        // TODO add your handling code here:
        rescaling();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Clock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Clock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Clock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Clock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Clock(1.0, TimeZone.getDefault(), true).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExitButt;
    private javax.swing.JLabel Jlabel;
    private javax.swing.JComboBox jComboBox;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JButton updateButt;
    // End of variables declaration//GEN-END:variables


}
