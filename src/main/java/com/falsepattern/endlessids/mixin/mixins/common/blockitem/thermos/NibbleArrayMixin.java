/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.thermos;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.chunk.NibbleArray;

@Mixin(NibbleArray.class)
public class NibbleArrayMixin {

    @Shadow
    public byte[] data; // Thermos renamed 'data'
    @Shadow private int depthBits;     // depthBits
    @Shadow private int depthBitsPlusFour;     // depthBitsPlusFour
    
    @Shadow private int length;
    @Shadow private byte trivialByte;
    @Shadow private byte trivialValue;
    
        @Inject(
                method = "detectAndProcessTrivialArray",
                at = @At(
                        value = "FIELD",
                        opcode = Opcodes.PUTFIELD,
                        target = "Lnet/minecraft/world/chunk/NibbleArray;data:[B",
                        shift = At.Shift.BEFORE
                ),
                cancellable = true)
        private void logBeforeNull(CallbackInfo ci) {
            ci.cancel();
        }

    @Inject(method = "<init>(II)V", at = @At("RETURN"))
    private void fixConstructor(int par1, int par2, CallbackInfo ci) {
        this.data = new byte[par1 >> 1];
        this.depthBits = par2;
        this.depthBitsPlusFour = par2 + 4;
        this.length = par1 >> 1;
        this.trivialValue = 0;
        this.trivialByte = 0;
    }
        
}
