package com.wenge.tbase.cicd.generator;

import com.wenge.tbase.commons.entity.ModuleEnum;
import com.wenge.tbase.commons.generator.MyBatisPlusGenerator;

public class CICDGeneratorMain {
    public static void main(String[] args) {
        MyBatisPlusGenerator.generator(ModuleEnum.MODULE_CICD, "cicd_pipeline_stage");
    }
}
