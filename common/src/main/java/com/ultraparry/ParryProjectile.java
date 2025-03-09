package com.ultraparry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.world.World;

public class ParryProjectile extends SmallFireballEntity {
    public ParryProjectile(EntityType<? extends SmallFireballEntity> entityType, World world) {
        super(entityType, world);
    }
}
