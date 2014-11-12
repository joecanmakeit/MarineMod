// Date: 11/5/2014 6:16:05 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.makersfactory.marinemod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDolphin extends ModelBase
{
  //fields
    ModelRenderer dorsal;
    ModelRenderer left_fin;
    ModelRenderer right_fin;
    ModelRenderer right_tail_fin;
    ModelRenderer left_tail_fin;
    ModelRenderer body;
    ModelRenderer lowerbody;
    ModelRenderer head;
    ModelRenderer nose;
  
  public ModelDolphin()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      dorsal = new ModelRenderer(this, 0, 0);
      dorsal.addBox(-0.5F, -10F, 2F, 1, 10, 4);
      dorsal.setRotationPoint(-3F, -4F, -12F);
      dorsal.setTextureSize(64, 32);
      dorsal.mirror = true;
      setRotation(dorsal, -0.669215F, 0F, 0F);
      left_fin = new ModelRenderer(this, 0, 0);
      left_fin.addBox(-4F, 0F, -2F, 9, 1, 4);
      left_fin.setRotationPoint(2F, 3F, -10F);
      left_fin.setTextureSize(64, 32);
      left_fin.mirror = true;
      setRotation(left_fin, 0.2230705F, -0.6692116F, 0.111544F);
      right_fin = new ModelRenderer(this, 0, 0);
      right_fin.addBox(-3F, -2F, -1F, 9, 1, 4);
      right_fin.setRotationPoint(-6F, 4F, -10F);
      right_fin.setTextureSize(64, 32);
      right_fin.mirror = true;
      setRotation(right_fin, -0.2230705F, -2.479108F, 0.111544F);
      right_tail_fin = new ModelRenderer(this, 0, 0);
      right_tail_fin.addBox(-9F, -1F, -5F, 12, 2, 5);
      right_tail_fin.setRotationPoint(-2.5F, 0F, 16F);
      right_tail_fin.setTextureSize(64, 32);
      right_tail_fin.mirror = true;
      setRotation(right_tail_fin, 0F, 0.7071165F, 0F);
      left_tail_fin = new ModelRenderer(this, 0, 0);
      left_tail_fin.addBox(-2F, -1F, -5F, 11, 2, 5);
      left_tail_fin.setRotationPoint(-3.5F, 0F, 16F);
      left_tail_fin.setTextureSize(64, 32);
      left_tail_fin.mirror = true;
      setRotation(left_tail_fin, 0F, -0.7071201F, 0F);
      body = new ModelRenderer(this, 0, 0);
      body.addBox(-8F, -6F, -14F, 10, 12, 20);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      lowerbody = new ModelRenderer(this, 0, 0);
      lowerbody.addBox(-7F, -4F, 6F, 8, 8, 11);
      lowerbody.setRotationPoint(0F, 0F, 0F);
      lowerbody.setTextureSize(64, 32);
      lowerbody.mirror = true;
      setRotation(lowerbody, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 1);
      head.addBox(-4F, -5F, -10F, 8, 10, 10);
      head.setRotationPoint(-3F, 0F, -14F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, -0.0743572F, -0.9666439F);
      nose = new ModelRenderer(this, 17, 16);
      nose.addBox(-2F, -2F, -5F, 4, 4, 5);
      nose.setRotationPoint(0F, 3F, -10F);
      nose.setTextureSize(64, 32);
      nose.mirror = true;
      setRotation(nose, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    dorsal.render(f5);
    left_fin.render(f5);
    right_fin.render(f5);
    right_tail_fin.render(f5);
    left_tail_fin.render(f5);
    body.render(f5);
    lowerbody.render(f5);
    head.render(f5);
    nose.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}