/*
 * Copyright 2009 Michael Chermside
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mcherm.zithiacharsheet.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TreeImages;
import com.mcherm.zithiacharsheet.client.model.WeaponTraining;
import com.mcherm.zithiacharsheet.client.model.ZithiaCharacter;
import com.mcherm.zithiacharsheet.client.model.ZithiaStat;
import com.mcherm.zithiacharsheet.client.model.weapon.SingleWeaponSkill;
import com.mcherm.zithiacharsheet.client.modeler.EquationIntValue;
import com.mcherm.zithiacharsheet.client.modeler.ObservableInt;
import com.mcherm.zithiacharsheet.client.modeler.TweakableIntValue;


/**
 * A Tree view for viewing the weapons as they are used. Built using my
 * TreeGrid class.
 */
public class WeaponUseTreeGrid extends TreeGrid {

    private final static int NUM_COLUMNS = 5;

    /** Constructor. */
    public WeaponUseTreeGrid(ZithiaCharacter zithiaCharacter) {
        super(new WeaponUseTreeGridItem(zithiaCharacter, zithiaCharacter.getWeaponTraining()),
                NUM_COLUMNS, GWT.<TreeImages> create(TreeImages.class));
    }


    /**
     * Display a header giving the meaning of the different columns.
     */
    protected List<WidgetOrText> getHeader() {
        return Arrays.asList(
                new WidgetOrText(""),
                new WidgetOrText("Levels"),
                new WidgetOrText("Speed"),
                new WidgetOrText("Hp"),
                new WidgetOrText("Stun")
        );
    }


    private static class WeaponUseTreeGridItem implements TreeGridItem {
        private final ZithiaCharacter zithiaCharacter;
        private final WeaponTraining wt;

        public WeaponUseTreeGridItem(ZithiaCharacter zithiaCharacter, WeaponTraining wt) {
            this.zithiaCharacter = zithiaCharacter;
            this.wt = wt;
        }

        public List<WidgetOrText> getContents() {
            WidgetOrText col_0 = new WidgetOrText(wt.getWeaponSkill().getName());
            WidgetOrText col_1 = new WidgetOrText(new TweakableIntField(wt.getLevels()));
            WidgetOrText col_2, col_3, col_4;

            if (wt.getWeaponSkill() instanceof SingleWeaponSkill) {
                SingleWeaponSkill sws = (SingleWeaponSkill) wt.getWeaponSkill();
                final int weaponSpd = sws.getWeapon().getSpd();
                ObservableInt charSpd = zithiaCharacter.getStat(ZithiaStat.SPD).getValue();
                TweakableIntValue cycleTime = new EquationIntValue(charSpd, new EquationIntValue.Equation1() {
                    public int getValue(int charSpd) {
                        return charSpd + weaponSpd;
                    }
                });
                col_2 = new WidgetOrText(new TweakableIntField(cycleTime));
                col_3 = new WidgetOrText(sws.getWeapon().getHpDmg().getStr());
                col_4 = new WidgetOrText(sws.getWeapon().getStunDmg().getStr());
            } else {
                col_2 = col_3 = col_4 = new WidgetOrText("");
            }
            return Arrays.asList(
                    col_0,
                    col_1,
                    col_2,
                    col_3,
                    col_4
            );
        }

        public boolean isLeaf() {
            return wt.getWeaponSkill() instanceof SingleWeaponSkill;
        }

        public Iterable<TreeGridItem> getChildren() {
            List<TreeGridItem> result = new ArrayList<TreeGridItem>();
            for (WeaponTraining childWt : wt.getChildren()) {
                result.add(new WeaponUseTreeGridItem(zithiaCharacter, childWt));
            }
            return result;
        }
    }
}
