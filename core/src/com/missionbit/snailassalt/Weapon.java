package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
/**
 * Created by douglas on 6/18/14.
 */
public class Weapon {
    public Sprite sprite;
    public boolean enable;
    public boolean enableSalt;
    public Rectangle bound;
    public Vector2 speed;
    protected float width, height;
    static public float touch, touchY, deltaX, deltaY, rot, currentWater, waterSupply, currentSalt, saltSupply;;
    static public int str;
    private Sound hydrasound;
    protected Sound watergunsound;
    public Weapon() {this("images/weapons/watergunArm.png");}
    public Weapon(String image) {
        watergunsound = Gdx.audio.newSound(Gdx.files.internal("sounds/weapons/water.mp3"));
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        sprite = new Sprite(new Texture(image));
        sprite.setPosition(width - 2 * sprite.getWidth() / 3, height / 2 + 20);
        sprite.setSize(width/1196 * sprite.getWidth(),height/720 * sprite.getHeight());
        bound = new Rectangle();
        bound.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        speed = new Vector2();
        speed.set(0, 5);
        touch = 0;
        touchY = 0;
        rot = 0;
        str = 2;
        //TODO FIX THIS
        waterSupply = 50;
        currentWater = waterSupply;
    }

    public void Update(ArrayList<ThrowyThingy> water) {
        if(!enable) {return;}
        if (Gdx.input.justTouched() && currentWater > 0 && SnailAssalt.weaponState == SnailAssalt.WeaponState.REGWEAPON && SnailAssalt.bulletType == SnailAssalt.BulletType.WATER && !SnailAssalt.hydraButton.isPressed() && !SnailAssalt.saltButton.isPressed()) {
            watergunsound.play(1.0f);
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            Gdx.app.log("rot",""+rot);
            ThrowyThingy proj = new ThrowyThingy();
            water.add(proj);
            proj.bound.setPosition(this.bound.x, this.bound.y);
            proj.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            currentWater -- ;
        }
    }

    public void Update2(ArrayList<SaltProjectile> shakers){
        if(!enableSalt) {return;}
        if (Gdx.input.justTouched() && currentSalt>0 && SnailAssalt.bulletType==SnailAssalt.BulletType.SALT && !SnailAssalt.saltButton.isPressed() && !SnailAssalt.hydraButton.isPressed()) {
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            SaltProjectile bullet= new SaltProjectile();
            shakers.add(bullet);
            bullet.bound.setPosition(this.bound.x, this.bound.y);
            bullet.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            currentSalt -- ;
        }
    }
}
