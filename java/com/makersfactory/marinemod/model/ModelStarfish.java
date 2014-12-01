package com.makersfactory.marinemod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStarfish extends ModelBase
{
  //fields
    ModelRenderer head2;
    ModelRenderer head;
  
  public ModelStarfish()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      head2 = new ModelRenderer(this, 0, 0);
      head2.addBox(-2F, 0F, -2F, 4, 1, 4);
      head2.setRotationPoint(0F, 23F, 0F);
      head2.setTextureSize(64, 32);
      head2.mirror = true;
      setRotation(head2, 0F, 0.7853982F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-2F, 0F, -2F, 4, 1, 4);
      head.setRotationPoint(0F, 23F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head2.render(f5);
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