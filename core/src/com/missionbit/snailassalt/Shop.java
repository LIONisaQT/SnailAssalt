package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ryanisaqt on 8/24/14.
 */
public class Shop extends GameStates {
    protected BackButton backButton;
    protected ShopHydraButton shopHydraButton;
    protected ShopSaltButton shopSaltButton;
    protected ShopHoseButton shopHoseButton;
    protected Sprite shopHydra, shopHose, shopSalt;
    public Shop() {
        background = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        background.setSize(width, height);
    }

    public void create() {
        BackButton tempBackButton = new BackButton(0, 0);
        backButton = new BackButton(width - tempBackButton.getWidth(), 10);

        //SHOP BUTTONS
        Sprite tempShopButton = new Sprite(new Texture("images/buttons/hoseIcon.png"));
        tempShopButton.setSize(width / 1196 * tempShopButton.getWidth(), height / 720 * tempShopButton.getHeight());

        //SHOP: BUY HYDRA BUTTON
        shopHydra = new Sprite(new Texture("images/buttons/weaponIcon.png"));
        shopHydraButton = new ShopHydraButton(width / 12 , height / 2);
        shopHydra.setSize(width / 1196 * shopHydra.getWidth(), height / 720 * shopHydra.getHeight());
        shopHydra.setPosition(shopHydraButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopHydraButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);

        //SHOP: BUY HOSE BUTTON
        shopHose = new Sprite(new Texture("images/buttons/hoseIcon.png"));
        shopHoseButton = new ShopHoseButton(shopHydraButton.getXPos() + tempShopButton.getWidth() + 20 , height / 2);
        shopHose.setSize(width / 1196 * shopHose.getWidth(), height / 720 * shopHose.getHeight());
        shopHose.setPosition(shopHoseButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopHoseButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);

        //SHOP: BUY SALT BUTTON
        shopSaltButton = new ShopSaltButton(shopHoseButton.getXPos() + tempShopButton.getWidth() + 20, height / 2);
        shopSalt = new Sprite(new Texture("images/buttons/saltIcon.png"));
        shopSalt.setSize(width / 1196 * shopSalt.getWidth(), height / 720 * shopSalt.getHeight());
        shopSalt.setPosition(shopSaltButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopSaltButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);
    }

    public void update() {
        if (backButton.touchup()) {
            backButton.pressedAction();
        }

        //DOUGLAS'S TRIAL STUFF -- LEAVE UNTIL FURTHER NOTICE
            /*if (backButtonShop.pressable() && backButtonShop.isPressed() && backButtonShop.status == false) {
                backButtonShop.status = true;

            }
            if (!backButtonShop.isPressed() && backButtonShop.status == true) {
                gameState = gameState.MAINMENU;
                backButtonShop.status = false;
            }*/

        //BUY HYDRA
        if (SnailAssalt.currency > shopHydraButton.price && shopHydraButton.isPressed()) {
            shopHydraButton.ownd = true;
            SnailAssalt.currency -= shopHydraButton.price;
            SnailAssalt.preferences.putInteger("hydra", 1);
            //preferences.flush();
        }
        if (SnailAssalt.preferences.getInteger("hydra", 0) == 1)
            SnailAssalt.hydra.enable = true;

        //BUY HOSE
        if (SnailAssalt.currency >= shopHoseButton.price && shopHoseButton.isPressed() && !shopHoseButton.ownd) {
            shopHoseButton.ownd = true;
            SnailAssalt.currency -= shopHoseButton.price;
            SnailAssalt.preferences.putInteger("hose", 1);
            //preferences.flush();
        }
        if (SnailAssalt.preferences.getInteger("hose", 0) == 1)
            SnailAssalt.hose.enable = true;

        //BUY SALT
        if (SnailAssalt.currency > shopSaltButton.price && shopSaltButton.isPressed()) {
            SnailAssalt.currency -= shopSaltButton.price;
            Weapon.saltSupply++;
            Weapon.currentSalt = Weapon.saltSupply;
            SnailAssalt.preferences.putInteger("saltsupply", (int) Weapon.saltSupply);
            Weapon.enableSalt = true;
            //preferences.flush();
        }
        if (SnailAssalt.preferences.getInteger("salt", 0) == 1) {
            SnailAssalt.waterGun.enableSalt = true;
            SnailAssalt.hydra.enableSalt = true;
            SnailAssalt.hose.enableSalt = true;
        }
    }

    public void draw() {
        SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
        SnailAssalt.font.setColor(0, 0, 0, 1);
        batch.begin();
        background.draw(batch);

        //BUTTONS
        backButton.sprite.draw(batch);
        shopHydraButton.sprite.draw(batch);
        shopSaltButton.sprite.draw(batch);
        shopHoseButton.sprite.draw(batch);

        //PRESSED BUTTONS
        if (shopHoseButton.ownd) {
            shopHoseButton.spriteNope.draw(batch);
        }
        if (shopHydraButton.ownd) {
            shopHydraButton.spriteNope.draw(batch);
        }
        if (backButton.isPressed()) {
            backButton.spriteShade.draw(batch);
        }

        //ITEM SPRITES
        shopSalt.draw(batch);
        shopHydra.draw(batch);
        shopHose.draw(batch);

        //PRICE TAGS
        SnailAssalt.font.draw(batch, "$" + shopHoseButton.price, shopHydraButton.getXPos(), shopHydraButton.getYPos() - 10);
        SnailAssalt.font.draw(batch, "$" + shopHoseButton.price, shopHoseButton.getXPos(), shopHoseButton.getYPos() - 10);
        SnailAssalt.font.draw(batch, "$" + shopSaltButton.price, shopSaltButton.getXPos(), shopSaltButton.getYPos() - 10);

        //POOR EXCUSE FOR INVENTORY
        SnailAssalt.font.draw(batch, " salts: " + (int) Weapon.saltSupply, shopSaltButton.getXPos() + shopSaltButton.sprite.getWidth(), shopSaltButton.getYPos() + shopSaltButton.buttonGetHeight());
        SnailAssalt.font.draw(batch, "shells: " + SnailAssalt.currency, 10, height);

        batch.end();
    }
}
