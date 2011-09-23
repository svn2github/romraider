/*
 * RomRaider Open-Source Tuning, Logging and Reflashing
 * Copyright (C) 2006-2010 RomRaider.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.romraider.logger.external.te.plugin;

import com.romraider.logger.ecu.definition.EcuDataConvertor;
import com.romraider.logger.ecu.definition.ExternalDataConvertorImpl;

public final class TEDataItemImpl implements TEDataItem {
    private final EcuDataConvertor[] convertors;
    private final String name;
    private int[] raw;

    public TEDataItemImpl(String name, TESensorConversions... convertorList) {
        super();
        this.name = name;
        convertors = new EcuDataConvertor[convertorList.length];
        int i = 0;
        for (TESensorConversions convertor :convertorList) {
        	convertors[i] = new ExternalDataConvertorImpl(
        			this,
        			convertor.units(),
        			convertor.expression(),
        			convertor.format()
        	);
        	i++;
        }
    }

    public String getName() {
        return "Tech Edge " + name;
    }

    public String getDescription() {
        return "Tech Edge " + name + " data";
    }

    public EcuDataConvertor[] getConvertors() {
        return convertors;
    }

    public double getData() {
    	return raw[0] * 256d + raw[1];
    }

    public void setRaw(int... raw) {
        this.raw = raw;
    }
}
