package world;

import java.awt.event.KeyListener;

import main.Sound;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    Sound sound = new Sound();
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.playSE(1);
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.playSE(1);
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
            }
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_SPACE) {
                    gp.playSE(1);
                    if (gp.ui.commandNumber == 0) {
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.commandNumber == 1) {
                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNumber == 2) {
                        System.exit(0);
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_SPACE) {
                    gp.playSE(1);
                    if (gp.ui.commandNumber == 0) {
                        gp.ui.titleScreenState = 0;
                    }
                    if (gp.ui.commandNumber == 1) {
                        gp.ui.titleScreenState = 0;
                    }
                }
            }

        }

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            if (gp.gameState == gp.playState) {
                gp.playSE(6);
                gp.gameState = gp.titleState;
            } else if (gp.gameState == gp.titleState) {
                gp.playSE(7);
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

}
