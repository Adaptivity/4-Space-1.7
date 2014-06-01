package mattparks.mods.mercury.dimension;

import mattparks.mods.MattparksCore.ConfigManager;
import mattparks.mods.mercury.world.gen.GCMercuryWorldChunkManager;
import mattparks.mods.venus.world.gen.GCVenusChunkProvider;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.GCCoreConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GCMercuryWorldProvider extends WorldProvider implements IGalacticraftWorldProvider, ISolarLevel
{
	@Override
	public void setDimension(int var1)
	{
		this.dimensionId = var1;
		super.setDimension(var1);
	}

	@Override
	protected void generateLightBrightnessTable()
	{
		final float var1 = 0.0F;

		for (int var2 = 0; var2 <= 15; ++var2)
		{
			final float var3 = 1.0F - var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}
	}

	@Override
	public float[] calcSunriseSunsetColors(float var1, float var2)
	{
		return null;
	}

	@Override
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new GCMercuryWorldChunkManager();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3 getFogColor(float var1, float var2)
	{
		return this.worldObj.getWorldVec3Pool().getVecFromPool((double) 0F / 255F, (double) 0F / 255F, (double) 0F / 255F);
	}

	@Override
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
	{
		return this.worldObj.getWorldVec3Pool().getVecFromPool(0, 0, 0);
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		final int var4 = (int) (par1 % 5832000L);
		float var5 = (var4 + par3) / 5832000.0F - 0.25F;

		if (var5 < 0.0F)
		{
			++var5;
		}

		if (var5 > 1.0F)
		{
			--var5;
		}

		final float var6 = var5;
		var5 = 1.0F - (float) ((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1)
	{
        float f1 = this.worldObj.getCelestialAngle(par1);
        float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.25F);

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 0.9F)
        {
            f2 = 0.9F;
        }

        return f2 * f2 * 1.0F;
	}

	public float calculatePhobosAngle(long par1, float par3)
	{
		return this.calculateCelestialAngle(par1, par3) * 3000;
	}

	public float calculateDeimosAngle(long par1, float par3)
	{
		return this.calculatePhobosAngle(par1, par3) * 0.0000000001F;
	}

    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new GCVenusChunkProvider(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }

	@Override
	public void updateWeather()
	{
		this.worldObj.getWorldInfo().setRainTime(0);
		this.worldObj.getWorldInfo().setRaining(false);
		this.worldObj.getWorldInfo().setThunderTime(0);
		this.worldObj.getWorldInfo().setThundering(true);
    	this.worldObj.rainingStrength = 0.0F;
    	this.worldObj.thunderingStrength = 0.0F;
	}

	@Override
	public boolean isSkyColored()
	{
		return true;
	}

	@Override
	public double getHorizon()
	{
		return 45.0D;
	}

	@Override
	public int getAverageGroundLevel()
	{
		return 45;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public boolean canCoordinateBeSpawn(int var1, int var2)
	{
		return true;
	}

	@Override
	public boolean canRespawnHere()
	{
		return !GCCoreConfigManager.forceOverworldRespawn;
	}

	@Override
	public String getSaveFolder()
	{
		return "DIM" + ConfigManager.dimensionIDMercury;
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Entering Mercury";
	}

	@Override
	public String getDepartMessage()
	{
		return "Leaving Mercury";
	}

	@Override
	public String getDimensionName()
	{
		return "Mercury";
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		return false;
	}

	@Override
	public boolean canDoLightning(Chunk chunk)
	{
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return false;
	}

    @Override
    public float getGravity()
    {
        return 0.058F;
    }

	@Override
	public int getHeight()
	{
		return 800;
	}

	@Override
	public double getMeteorFrequency()
	{
		return 99.9D;
	}

	@Override
	public double getFuelUsageMultiplier()
	{
		return 0.9D;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier)
	{
		return tier >= ConfigManager.MercuryTierLevel;
	}

	@Override
	public float getFallDamageModifier()
	{
		return 0.9F;
	}

	@Override
	public float getSoundVolReductionAmount()
	{
		return 6.0F;
	}

	@Override
	public double getSolarEnergyMultiplier() 
	{
		return 26;
	}
}
