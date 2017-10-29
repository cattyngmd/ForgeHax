package com.matt.forgehax.mods;

import com.matt.forgehax.Helper;
import com.matt.forgehax.events.Render2DEvent;
import com.matt.forgehax.util.Utils;
import com.matt.forgehax.util.command.Setting;
import com.matt.forgehax.util.draw.SurfaceHelper;
import com.matt.forgehax.util.mod.Category;
import com.matt.forgehax.util.mod.ToggleMod;
import com.matt.forgehax.util.mod.loader.RegisterMod;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Babbaj on 10/28/2017.
 */
@RegisterMod
public class CompassMod extends ToggleMod {

    public final Setting<Integer> scale = getCommandStub().builders().<Integer>newSettingBuilder()
            .name("scale")
            .description("size of the compass")
            .defaultTo(3)
            .build();

    private final double HALF_PI = Math.PI/2;

    private final String[] DIRECTIONS = {
            "N",
            "W",
            "S",
            "E"
    };

    public CompassMod() { super(Category.RENDER, "Compass", false, "cool compass overlay"); }

    @SubscribeEvent
    public void onRender(Render2DEvent event) {
        double centerX = event.getScreenWidth()/2;
        double centerY = event.getScreenHeight()*0.8;

        for (String str : DIRECTIONS) {
            double rad = getPosOnCompass(str);
            SurfaceHelper.drawTextShadowCentered(str, (float) (centerX + getX(rad)), (float)(centerY + getY(rad)),
                    str.equals("N") ? Utils.Colors.RED :Utils.Colors.WHITE);
        }
    }

    private double getX(double rad) {
        return Math.sin(rad) * (scale.get()*10);
    }

    private double getY(double rad) {
        double pitch = Math.toRadians(Helper.getLocalPlayer().rotationPitch); // player pitch
        return Math.cos(rad) * (scale.get()*10) * Math.abs(Math.sin(pitch));
    }

    // return the position on the circle in radians
    private double getPosOnCompass(String s) {
        double yaw = Math.toRadians(MathHelper.wrapDegrees(Helper.getLocalPlayer().rotationYaw - 90)); // player yaw
        int index = ArrayUtils.indexOf(DIRECTIONS, s)+1; // directions index in the list
        return yaw + index * HALF_PI;
    }

}
