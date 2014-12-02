package com.makersfactory.marinemod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelFish extends ModelBase
  {
  ModelRenderer Body1;
  ModelRenderer Body2;
  ModelRenderer TopFin;
  ModelRenderer BottomFin;
  ModelRenderer Tail;

  public ModelFish()
  {
	this.Body1 = new ModelRenderer(this, 0, 12);
    this.Body1.addBox(0.0F, -0.5F, 0.0F, 4, 2, 4);
	this.Body1.setRotationPoint(-3.0F, 15.0F, 0.0F);
	setRotation(this.Body1, 0.0F, 0.7853982F, 0.0F);
    this.Body2 = new ModelRenderer(this, 0, 2);
    this.Body2.addBox(0.0F, -1.5F, -1.0F, 5, 3, 2);
    this.Body2.setRotationPoint(-3.0F, 15.0F, 0.0F);
    this.TopFin = new ModelRenderer(this, 0, 18);
    this.TopFin.addBox(-5.0F, -2.0F, 0.0F, 8, 3, 0);
    this.TopFin.setRotationPoint(0.0F, 13.5F, 0.0F);
    this.BottomFin = new ModelRenderer(this, 16, 18);
    this.BottomFin.addBox(-5.0F, 0.0F, 0.0F, 8, 3, 0);
    this.BottomFin.setRotationPoint(0.0F, 15.5F, 0.0F);
    this.Tail = new ModelRenderer(this, 10, 7);
    this.Tail.addBox(0.0F, 0.0F, -0.5F, 3, 3, 1);
    this.Tail.setRotationPoint(1.3F, 15.0F, 0.0F);
    setRotation(this.Tail, 0.0F, 0.0F, -0.7853982F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    this.Body1.render(f5);
    this.Body2.render(f5);
    this.TopFin.render(f5);
    this.BottomFin.render(f5);
    this.Tail.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    float tail = MathHelper.cos(f * 0.8F) * f1 * 0.6F;
    float fin = MathHelper.cos(f2 * 0.4F) * 0.2F;
    this.Body1.rotateAngleY = (0.7853982F + fin);
    this.Tail.rotateAngleY = tail;
  }
}
