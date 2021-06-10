package com.fh.shop.api.minio.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class MinioUploadDto implements Serializable {

    private String name;

    private String url;

}
