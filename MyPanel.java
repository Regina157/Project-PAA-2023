/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package droidgame_paa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Asus
 */


public class MyPanel extends JPanel {
    private static final int CELL_SIZE = 35;
    private static final Color COLOR_WALL = Color.BLACK;
    private static final Color COLOR_ROAD = Color.WHITE;
    private static final Color COLOR_RED_DROID = Color.RED;
    private static final Color COLOR_GREEN_DROID = Color.GREEN;

    private int[][] currentMap;
    private int[][][] maps;
    private int mapIndex;
    private Point redDroidPosition;
    private Point greenDroidPosition;
    private int greenDroidViewDistance;
    private boolean isGameStarted;
    private boolean isGamePaused;
    private int greenDroidVisibility;
    private int MAP_SIZE;

    public MyPanel() {
        setPreferredSize(new Dimension(15 * CELL_SIZE, 15 * CELL_SIZE));

        // Initialize the maps
        maps = new int[][][] {//pegambaran peta secara statis
            {
                    {1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 2, 0, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                    {0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                    {0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
                    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 3, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1}
            },
            {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 2, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0},
                    {0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0},
                    {0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0},
                    {0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0},
                    {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                    {0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                    {0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                    {0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0},
                    {0, 3, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            },
            {
                    {0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0},
                    {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 2, 0, 0},
                    {0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0},
                    {0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0},
                    {0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0},
                    {1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0},
                    {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0},
                    {1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0},
                    {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0},
                    {0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                    {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0},
                    {0, 3, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            }
        };

        // Initialize the current map and droid positions
        mapIndex = 0;
        currentMap = maps[mapIndex];
        redDroidPosition = findDroidPosition(2);
        greenDroidPosition = findDroidPosition(3);
        greenDroidViewDistance = 5;

        // Set up the buttons and sliders
        
        //pembuatan Tombol Fitur Start
        JButton startButton = new JButton("START");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGameStarted = true;
                isGamePaused = false;
                startButton.setEnabled(false);
            }
        });

        
        //Pembuatan Tombol Fitur Pause
        JButton pauseButton = new JButton("PAUSE");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGamePaused = !isGamePaused;
                pauseButton.setText(isGamePaused ? "RESUME" : "PAUSE");
            }
        });

        
        //Pembuatan Tombol Fitur Acak Peta
        JButton randomMapButton = new JButton("ACAK PETA");
        randomMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeMap();
                repaint();
            }
        });

        
        //Pembuatan Tombol Fitur Acak
        JButton randomRedDroidButton = new JButton("ACAK DROID MERAH");
        randomRedDroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeRedDroidPosition();
                repaint();
            }
        });

        
        //Pembuatan Tombol Fitur Acak Droid Hijau
        JButton randomGreenDroidButton = new JButton("ACAK DROID HIJAU");
        randomGreenDroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeGreenDroidPosition();
                repaint();
            }
        });

        //Pembuatan Slider untuk Mengatur Jarak Pandang Droid Hijau
        JSlider greenDroidViewSlider = new JSlider(1, 5, greenDroidViewDistance);
        greenDroidViewSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                greenDroidViewDistance = greenDroidViewSlider.getValue();
            }
        });

        
        //Pembuatan Tombol Fitur untuk Tambahkan Droid Merah
        JButton addRedDroidButton = new JButton("TAMBAHKAN DROID MERAH");
        addRedDroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRedDroid();
                repaint();
            }
        });
        

        //Pembuatan Tombol Fitur Untuk Menghapus Droid Merah
        JButton removeRedDroidButton = new JButton("HAPUS DROID MERAH");
        removeRedDroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRedDroid();
                repaint();
            }
        });

        
        //Pembuatan Tombol Fitur untuk Melihat POV Droid Merah
        JButton POVRedDroidButton = new JButton("POV DROID MERAH");
        POVRedDroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                POVRedDroid();
                repaint();
        }
        });
         
        // Set up the layout
        // SetBounds digunakan untuk mengatur posisi letak Tombol fitur yang ada dalam permainan "DROID GAME"
        setLayout(null);
        JPanel buttonPanel = new JPanel();
        
        startButton.setBounds(560, 0, 220, 30);
                add(startButton);
                
        pauseButton.setBounds(560, 35, 220, 30);
                add(pauseButton);
                
        randomMapButton.setBounds(560, 70, 220, 30);
                add(randomMapButton);
                
        randomRedDroidButton.setBounds(560, 105, 220, 30);
                add(randomRedDroidButton);
                
        randomGreenDroidButton.setBounds(560, 140, 220, 30);
                add(randomGreenDroidButton);
                
        JLabel JarakPandangLabel = new JLabel("SET GREEN DROID VIEW");
        JarakPandangLabel.setBounds(600, 175, 220, 30);
                add(JarakPandangLabel);
                
        greenDroidViewSlider.setBounds(560, 210, 220, 30);
                add(greenDroidViewSlider);
                
        addRedDroidButton.setBounds(560, 245, 220, 30);
                add(addRedDroidButton);
                
        removeRedDroidButton.setBounds(560, 280, 220, 30);
                add(removeRedDroidButton);
                
        POVRedDroidButton.setBounds(560, 315, 220, 30);
                add(POVRedDroidButton);
                
        JLabel reginaLabel = new JLabel("REGINA 2101020078");
        reginaLabel.setBounds(580, 450, 220, 30);
                add(reginaLabel);
       // add(buttonPanel, BorderLayout.SOUTH);

        // Set up the game loop
        
        
        //fungsi Timer digunakan untuk mengatur pergerakan Droid
        Timer timer = new Timer(50, new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) { //fungsi untuk memulai permainan dan Menghentikan permainan
                if (isGameStarted && !isGamePaused) {
                    if (redDroidPosition.equals(greenDroidPosition)) {
                        JOptionPane.showMessageDialog(MyPanel.this, "GAME OVER");
                        isGameStarted = false;
                        startButton.setEnabled(true);
                    } else {
                        moveRedDroid();
                        moveGreenDroid();
                        repaint();
                    }
                }
            }
        });
        timer.start();
    }
    
    
    private void POVRedDroid() { // untuk melihat jarak pandang droid merah.// Mengganti nilai selain 2 (Droid Merah) dengan -1 untuk menandakan bahwa sel tersebut tidak terlihat oleh Droid Merah
    for (int row = 0; row < currentMap.length; row++) {
        for (int col = 0; col < currentMap[0].length; col++) {
            if (currentMap[row][col] != 2) {
                currentMap[row][col] = -1;
            }
        }
    }
}
 
    
//digunakan untuk mencari posisi (koordinat baris dan kolom) dari droid yang diberikan pada peta yang sedang digunakan.
    private Point findDroidPosition(int droid) { 
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (currentMap[i][j] == droid) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    } 

    
    private void randomizeMap() { // digunakan untuk mengacak peta
        mapIndex = (mapIndex + 1) % maps.length;
        currentMap = maps[mapIndex];
        redDroidPosition = findDroidPosition(2);
        greenDroidPosition = findDroidPosition(3);
    }

    
    private void randomizeRedDroidPosition() { //untuk mengacak posisi droid merah
        int row = (int) (Math.random() * currentMap.length);
        int col = (int) (Math.random() * currentMap[0].length);
        currentMap[redDroidPosition.x][redDroidPosition.y] = 1;
        currentMap[row][col] = 2;
        redDroidPosition = new Point(row, col);
    }

    
    private void randomizeGreenDroidPosition() { // untuk mengacak posisi droid hijau
        int row = (int) (Math.random() * currentMap.length);
        int col = (int) (Math.random() * currentMap[0].length);
        currentMap[greenDroidPosition.x][greenDroidPosition.y] = 1;
        currentMap[row][col] = 3;
        greenDroidPosition = new Point(row, col);
    }

    
    private void addRedDroid() { // digunakan untuk menambahkan droid merah
    int row;
    int col;
    do {
        row = (int) (Math.random() * currentMap.length);
        col = (int) (Math.random() * currentMap[0].length);
    } while (currentMap[row][col] != 1); // Cek apakah posisi tersebut adalah jalan
    currentMap[row][col] = 2;
}


    private void removeRedDroid() { //untuk menghapus droid merah
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (currentMap[i][j] == 2) {
                    currentMap[i][j] = 1;
                    return;
                }
            }
        }
    }

    
    private void moveRedDroid() { // pergerakan droid merah
        int newRow = redDroidPosition.x;
        int newCol = redDroidPosition.y;
        do {
            int direction = (int) (Math.random() * 4);
            switch (direction) {
                case 0: // Up
                    newRow--;
                    break;
                case 1: // Right
                    newCol++;
                    break;
                case 2: // Down
                    newRow++;
                    break;
                case 3: // Left
                    newCol--;
                    break;
            }
        } while (!isValidPosition(newRow, newCol));
        currentMap[redDroidPosition.x][redDroidPosition.y] = 1;
        currentMap[newRow][newCol] = 2;
        redDroidPosition = new Point(newRow, newCol);
    }

    
    private void moveGreenDroid() { // pergerakan droid hijau
        int newRow = greenDroidPosition.x;
        int newCol = greenDroidPosition.y;
        do {
            int direction = (int) (Math.random() * 4);
            switch (direction) {
                case 0: // Up
                    newRow--;
                    break;
                case 1: // Right
                    newCol++;
                    break;
                case 2: // Down
                    newRow++;
                    break;
                case 3: // Left
                    newCol--;
                    break;
            }
        } while (!isValidPosition(newRow, newCol));
        currentMap[greenDroidPosition.x][greenDroidPosition.y] = 1;
        currentMap[newRow][newCol] = 3;
        greenDroidPosition = new Point(newRow, newCol);
    }

    
 //  untuk memvalidasi posisi dalam peta dengan memeriksa batas-batas peta dan memastikan bahwa posisi tersebut tidak kosong.
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < currentMap.length && col >= 0 && col < currentMap[0].length && currentMap[row][col] != 0;
    }
    
    
    public void setGreenDroidVisibility(int visibility) {//digunakan untuk mengatur visibilitas droid hijau.
        greenDroidVisibility = visibility;
        repaint();
    }
 
    private boolean POVRedDroid(int row, int col) {
        // Memeriksa apakah sel dengan koordinat (row, col) terlihat oleh Droid Merah
        if (currentMap[row][col] != 0) {
            // Memeriksa apakah ada Droid Hijau di sekitar sel yang diperiksa
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (newRow >= 0 && newRow < MAP_SIZE && newCol >= 0 && newCol < MAP_SIZE) {
                        if (currentMap[newRow][newCol] == 3) {
                            return false; // Ada Droid Hijau di sekitar, tidak terlihat oleh Droid Merah
                        }
                    }
                }
            }
            return true; // Tidak ada Droid Hijau di sekitar, terlihat oleh Droid Merah
        }
        return false; // Sel adalah tembok, tidak terlihat oleh Droid Merah
    }
 
    
    @Override
    protected void paintComponent(Graphics g) {//untuk mengambarkan peta dan posisi droid
        super.paintComponent(g);

        int cellSize = Math.min(getWidth() / currentMap[0].length, getHeight() / currentMap.length);

        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                if (currentMap[i][j] == 0) {
                    g.setColor(Color.BLACK);
                } else if (currentMap[i][j] == 1) {
                    g.setColor(Color.WHITE);
                } else if (currentMap[i][j] == 2) {
                    g.setColor(Color.RED);
                } else if (currentMap[i][j] == 3) {
                    g.setColor(Color.GREEN);
                }

                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.BLACK);
        //      g.drawRect(x, y, cellSize, cellSize);
            }
        }

        if (redDroidPosition != null) {
            int x = redDroidPosition.y * cellSize;
            int y = redDroidPosition.x * cellSize;
            g.setColor(Color.RED);
            g.fillOval(x, y, cellSize, cellSize);
        }

        if (greenDroidPosition != null) {
            int x = greenDroidPosition.y * cellSize;
            int y = greenDroidPosition.x * cellSize;
            g.setColor(Color.GREEN);
            g.fillOval(x, y, cellSize, cellSize);

            if (greenDroidVisibility > 0) {
                g.setColor(Color.BLUE);
                g.drawOval(x - greenDroidVisibility * cellSize, y - greenDroidVisibility * cellSize,
                (2 * greenDroidVisibility + 1) * cellSize, (2 * greenDroidVisibility + 1) * cellSize);
            }
        }
    }
}