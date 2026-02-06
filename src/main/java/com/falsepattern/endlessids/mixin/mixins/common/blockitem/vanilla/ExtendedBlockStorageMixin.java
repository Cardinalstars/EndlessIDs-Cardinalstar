/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.config.GeneralConfig;
import lombok.var;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

@Mixin(ExtendedBlockStorage.class)
public class ExtendedBlockStorageMixin
{
    /**
     * @author Cardinalstar16
     * @reason Id Extension
     * Thermos does this already, so I just moved this into a mixin that is only loaded if thermos
     * is not enabled.
     */
    @Redirect(method = "removeInvalidBlocks",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockByExtId(III)Lnet/minecraft/block/Block;"),
              require = 1)
    private Block removeInvalidBlocks(ExtendedBlockStorage instance, int x, int y, int z) {
        var block = instance.getBlockByExtId(x, y, z);
        if (block == null && GeneralConfig.removeInvalidBlocks) {
            instance.func_150818_a(x, y, z, Blocks.air);
            block = Blocks.air;
        }
        return block;
    }
}
