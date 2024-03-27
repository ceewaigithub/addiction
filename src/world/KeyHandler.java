package world;

import java.awt.event.KeyListener;

import main.Sound;
import shop.ShopItem;
import shop.SpriteItem;

import java.awt.event.KeyEvent;

/**
 * The KeyHandler class implements the KeyListener interface and handles keyboard input for the game.
 */
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    Sound sound = new Sound();
    boolean checkDrawTime = false;

    /**
     * Constructs a KeyHandler object with the specified GamePanel.
     * 
     * @param gp the GamePanel object to associate with the KeyHandler
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Invoked when a key has been typed.
     * This method is not implemented in this class.
     * 
     * @param e the KeyEvent object representing the key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    /**
     * Invoked when a key has been pressed.
     * 
     * @param e the KeyEvent object representing the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
    
        if (gp.gameLogic.getGameState() == gp.gameLogic.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.playSE(1);
                    gp.ui.commandNumber--;
                    if (gp.ui.commandNumber < 0) {
                        gp.ui.commandNumber = 3;
                    }
                }
    
                if (code == KeyEvent.VK_S) {
                    gp.playSE(1);
                    gp.ui.commandNumber++;
                    if (gp.ui.commandNumber > 3) {
                        gp.ui.commandNumber = 0;
                    }
                }
    
                if (code == KeyEvent.VK_SPACE) {
                    gp.playSE(1);
                    if (gp.ui.commandNumber == 0) {
                        gp.gameLogic.setGameState(gp.gameLogic.playState);
                    }
                    if (gp.ui.commandNumber == 1) {
                        gp.ui.titleScreenState = 1;
                        gp.ui.commandNumber = 0;
                    }
                    if (gp.ui.commandNumber == 2) {
                        gp.config.setGameConfig();
                    }
                    if (gp.ui.commandNumber == 3) {
                        System.exit(0);
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.playSE(1);
                    gp.ui.commandNumber--;
                    if (gp.ui.commandNumber < 0) {
                        gp.ui.commandNumber = gp.sm.getShopItems().size();
                    }
                }
    
                if (code == KeyEvent.VK_S) {
                    gp.playSE(1);
                    gp.ui.commandNumber++;
                    if (gp.ui.commandNumber > gp.sm.getShopItems().size()) {
                        gp.ui.commandNumber = 0;
                    }
                }
    
               if (code == KeyEvent.VK_SPACE) {
                    if (gp.ui.commandNumber == gp.sm.getShopItems().size()) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNumber = 0;
                    } else {
                        ShopItem selectedItem = gp.sm.getShopItems().get(gp.ui.commandNumber);
                        if (selectedItem instanceof SpriteItem) {
                            SpriteItem selectedSpriteItem = (SpriteItem) selectedItem;
                            if (selectedSpriteItem.isPurchased()) {
                                gp.user.sprite = selectedSpriteItem.getSprite();
                                gp.user.getPlayerImage();
                                gp.playSE(1);
                            } else {
                                boolean bought = gp.sm.buyItem(gp.ui.commandNumber);
                                if (bought) {
                                    gp.playSE(1);
                                } else {
                                    gp.playSE(3);
                                }
                            }
                        } else {
                            boolean bought = gp.sm.buyItem(gp.ui.commandNumber);
                            if (bought) {
                                gp.playSE(1);
                            } else {
                                gp.playSE(3);
                            }
                        }
                    }
                }
            }
        } else if (gp.gameLogic.getGameState() == gp.gameLogic.gameOverState) {

            if (code == KeyEvent.VK_W) {
                gp.playSE(1);
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.playSE(1);
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0;
                }
            }

            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNumber == 0) {
                    gp.playSE(1);
                    gp.restartGame();
                    gp.gameLogic.setGameState(gp.gameLogic.playState);
                } else if (gp.ui.commandNumber == 1) {
                    gp.playSE(1);
                    System.exit(0);
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
            if (gp.getGameState() == gp.gameLogic.playState) {
                gp.playSE(6);
                gp.setGameState(gp.gameLogic.titleState);
            } else if (gp.getGameState() == gp.gameLogic.titleState) {
                gp.playSE(7);
                gp.setGameState(gp.gameLogic.playState);
            }
        }
    }

    /**
     * Invoked when a key has been released.
     * 
     * @param e the KeyEvent object representing the key event
     */
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
