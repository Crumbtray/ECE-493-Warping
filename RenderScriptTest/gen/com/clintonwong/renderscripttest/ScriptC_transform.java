/*
 * Copyright (C) 2011-2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: /Users/Crumbs/ECE-493-Warping/RenderScriptTest/src/com/clintonwong/renderscripttest/transform.rs
 */
package com.clintonwong.renderscripttest;

import android.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_transform extends ScriptC {
    private static final String __rs_resource_name = "transform";
    // Constructor
    public  ScriptC_transform(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_transform(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        __I32 = Element.I32(rs);
    }

    private Element __I32;
    private FieldPacker __rs_fp_I32;
    private final static int mExportVarIdx_input = 0;
    private Allocation mExportVar_input;
    public void bind_input(Allocation v) {
        mExportVar_input = v;
        if (v == null) bindAllocation(null, mExportVarIdx_input);
        else bindAllocation(v, mExportVarIdx_input);
    }

    public Allocation get_input() {
        return mExportVar_input;
    }

    private final static int mExportVarIdx_output = 1;
    private Allocation mExportVar_output;
    public void bind_output(Allocation v) {
        mExportVar_output = v;
        if (v == null) bindAllocation(null, mExportVarIdx_output);
        else bindAllocation(v, mExportVarIdx_output);
    }

    public Allocation get_output() {
        return mExportVar_output;
    }

    private final static int mExportVarIdx_width = 2;
    private int mExportVar_width;
    public synchronized void set_width(int v) {
        setVar(mExportVarIdx_width, v);
        mExportVar_width = v;
    }

    public int get_width() {
        return mExportVar_width;
    }

    public Script.FieldID getFieldID_width() {
        return createFieldID(mExportVarIdx_width, null);
    }

    private final static int mExportVarIdx_height = 3;
    private int mExportVar_height;
    public synchronized void set_height(int v) {
        setVar(mExportVarIdx_height, v);
        mExportVar_height = v;
    }

    public int get_height() {
        return mExportVar_height;
    }

    public Script.FieldID getFieldID_height() {
        return createFieldID(mExportVarIdx_height, null);
    }

    private final static int mExportFuncIdx_swirl = 0;
    public void invoke_swirl(int factor) {
        FieldPacker swirl_fp = new FieldPacker(4);
        swirl_fp.addI32(factor);
        invoke(mExportFuncIdx_swirl, swirl_fp);
    }

    private final static int mExportFuncIdx_setPixelAt = 1;
    public void invoke_setPixelAt(int x, int y, Short4 pixel) {
        FieldPacker setPixelAt_fp = new FieldPacker(12);
        setPixelAt_fp.addI32(x);
        setPixelAt_fp.addI32(y);
        setPixelAt_fp.addU8(pixel);
        invoke(mExportFuncIdx_setPixelAt, setPixelAt_fp);
    }

}

