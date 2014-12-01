package com.makersfactory.marinemod.model;

import net.minecraft.client.model.ModelBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelShark extends ModelBase
{
  public ModelRenderer HeadLeft;
  public ModelRenderer HeadRight;
  public ModelRenderer HeadUpper;
  public ModelRenderer HeadLower;
  public ModelRenderer TailRight;
  public ModelRenderer TailLeft;
  public ModelRenderer TailMisc;
  public ModelRenderer Body;
  public ModelRenderer UpperFin;
  public ModelRenderer TailFinUpper;
  public ModelRenderer TailFinLower;
  public ModelRenderer FinRight;
  public ModelRenderer FinLeft;

  public ModelShark()
  {
    this.Body = new ModelRenderer(this, 6, 6);
    this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
    this.Body.setRotationPoint(-4.0F, 17.0F, -10.0F);
    this.HeadUpper = new ModelRenderer(this, 0, 0);
    this.HeadUpper.addBox(0.0F, 0.0F, 0.0F, 5, 2, 8, 0.0F);
    this.HeadUpper.setRotationPoint(-3.5F, 21.0F, -16.5F);
    this.HeadUpper.rotateAngleX = 0.5235988F;
    this.HeadLower = new ModelRenderer(this, 44, 0);
    this.HeadLower.addBox(0.0F, 0.0F, 0.0F, 5, 2, 5, 0.0F);
    this.HeadLower.setRotationPoint(-3.5F, 21.5F, -13.5F);
    this.HeadLower.rotateAngleX = -0.261799F;
    this.HeadRight = new ModelRenderer(this, 0, 3);
    this.HeadRight.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
    this.HeadRight.setRotationPoint(-3.45F, 21.299999F, -13.85F);
    this.HeadRight.rotateAngleX = 0.7853981F;
    this.HeadLeft = new ModelRenderer(this, 0, 3);
    this.HeadLeft.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
    this.HeadLeft.setRotationPoint(0.45F, 21.299999F, -13.8F);
    this.HeadLeft.rotateAngleX = 0.7853981F;
    this.TailMisc = new ModelRenderer(this, 36, 8);
    this.TailMisc.addBox(0.0F, 0.0F, 0.0F, 4, 6, 10, 0.0F);
    this.TailMisc.setRotationPoint(-3.0F, 18.0F, 8.0F);
    this.UpperFin = new ModelRenderer(this, 6, 12);
    this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
    this.UpperFin.setRotationPoint(-1.5F, 17.0F, -1.0F);
    this.UpperFin.rotateAngleX = 0.7853981F;
    this.TailFinUpper = new ModelRenderer(this, 6, 12);
    this.TailFinUpper.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
    this.TailFinUpper.setRotationPoint(-1.5F, 18.0F, 16.0F);
    this.TailFinUpper.rotateAngleX = 0.5235988F;
    this.TailFinLower = new ModelRenderer(this, 8, 14);
    this.TailFinLower.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
    this.TailFinLower.setRotationPoint(-1.5F, 21.0F, 18.0F);
    this.TailFinLower.rotateAngleX = -0.7853981F;
    this.FinLeft = new ModelRenderer(this, 18, 0);
    this.FinLeft.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
    this.FinLeft.setRotationPoint(2.0F, 24.0F, -5.0F);
    this.FinLeft.rotateAngleY = -0.5235988F;
    this.FinLeft.rotateAngleZ = 0.5235988F;
    this.FinRight = new ModelRenderer(this, 18, 0);
    this.FinRight.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
    this.FinRight.setRotationPoint(-10.0F, 27.5F, -1.0F);
    this.FinRight.rotateAngleY = 0.5235988F;
    this.FinRight.rotateAngleZ = -0.5235988F;
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    // super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    this.Body.render(f5);
    this.TailMisc.render(f5);
    this.HeadUpper.render(f5);
    this.HeadLower.render(f5);
    this.HeadRight.render(f5);
    this.HeadLeft.render(f5);
    this.UpperFin.render(f5);
    this.TailFinUpper.render(f5);
    this.TailFinLower.render(f5);
    this.FinLeft.render(f5);
    this.FinRight.render(f5);
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    this.TailFinUpper.rotateAngleY = (MathHelper.cos(f * 0.6662F) * f1);
    this.TailFinLower.rotateAngleY = (MathHelper.cos(f * 0.6662F) * f1);
  }
}
