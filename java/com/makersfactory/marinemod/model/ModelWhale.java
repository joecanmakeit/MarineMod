package com.makersfactory.marinemod.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;


public class ModelWhale extends ModelBase
{
  //fields
    ModelRenderer neck;
    ModelRenderer body1;
    ModelRenderer leftpectoralfin;
    ModelRenderer rightpectoralfin;
    ModelRenderer pectoralfinr;
    ModelRenderer body3;
    ModelRenderer body4;
    ModelRenderer tail;
    ModelRenderer body6;
    ModelRenderer body5;
    ModelRenderer head;
  
  public ModelWhale()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      neck = new ModelRenderer(this, 0, 0);
      neck.addBox(-3F, -3F, -9F, 8, 7, 9);
      neck.setRotationPoint(-1F, 14F, -7F);
      neck.setTextureSize(64, 32);
      neck.mirror = true;
      setRotation(neck, 0F, 0F, 0F);
      body1 = new ModelRenderer(this, 28, 8);
      body1.addBox(-5F, -10F, -7F, 10, 14, 8);
      body1.setRotationPoint(0F, 11F, 3F);
      body1.setTextureSize(64, 32);
      body1.mirror = true;
      setRotation(body1, 1.570796F, -0.0174533F, 0F);
      leftpectoralfin = new ModelRenderer(this, 28, 8);
      leftpectoralfin.addBox(0F, 0F, -1F, 8, 3, 1);
      leftpectoralfin.setRotationPoint(5F, 16F, -6F);
      leftpectoralfin.setTextureSize(64, 32);
      leftpectoralfin.mirror = true;
      setRotation(leftpectoralfin, 1.797689F, 0F, 0F);
      rightpectoralfin = new ModelRenderer(this, 0, 0);
      rightpectoralfin.addBox(-5F, -3F, 0F, 10, 7, 10);
      rightpectoralfin.setRotationPoint(0F, 14F, 7F);
      rightpectoralfin.setTextureSize(64, 32);
      rightpectoralfin.mirror = true;
      setRotation(rightpectoralfin, 0F, 0F, 0F);
      pectoralfinr = new ModelRenderer(this, 28, 8);
      pectoralfinr.addBox(0F, 0F, 0F, 8, 3, 1);
      pectoralfinr.setRotationPoint(-4F, 16F, -5F);
      pectoralfinr.setTextureSize(64, 32);
      pectoralfinr.mirror = true;
      setRotation(pectoralfinr, -1.797689F, 3.141593F, 0F);
      body3 = new ModelRenderer(this, 0, 0);
      body3.addBox(-4F, -3F, 0F, 8, 6, 9);
      body3.setRotationPoint(0F, 15F, 17F);
      body3.setTextureSize(64, 32);
      body3.mirror = true;
      setRotation(body3, 0F, 0F, 0F);
      body4 = new ModelRenderer(this, 0, 0);
      body4.addBox(-3F, -3F, 0F, 6, 5, 8);
      body4.setRotationPoint(0F, 16F, 26F);
      body4.setTextureSize(64, 32);
      body4.mirror = true;
      setRotation(body4, 0F, 0F, 0F);
      tail = new ModelRenderer(this, 0, 0);
      tail.addBox(-6F, 0F, 0F, 12, 2, 4);
      tail.setRotationPoint(0F, 16F, 45F);
      tail.setTextureSize(64, 32);
      tail.mirror = true;
      setRotation(tail, -0.1047198F, 0F, 0F);
      body6 = new ModelRenderer(this, 0, 0);
      body6.addBox(-2F, -2F, 0F, 4, 4, 8);
      body6.setRotationPoint(0F, 16F, 33F);
      body6.setTextureSize(64, 32);
      body6.mirror = true;
      setRotation(body6, 0F, 0F, 0F);
      body5 = new ModelRenderer(this, 0, 0);
      body5.addBox(0F, 0F, 0F, 4, 3, 6);
      body5.setRotationPoint(-2F, 15F, 40F);
      body5.setTextureSize(64, 32);
      body5.mirror = true;
      setRotation(body5, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-3F, -2F, -8F, 6, 6, 8);
      head.setRotationPoint(0F, 14F, -16F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    neck.render(f5);
    body1.render(f5);
    leftpectoralfin.render(f5);
    rightpectoralfin.render(f5);
    pectoralfinr.render(f5);
    body3.render(f5);
    body4.render(f5);
    tail.render(f5);
    body6.render(f5);
    body5.render(f5);
    head.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}