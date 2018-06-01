package com.chero.client.convert;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.Personage;
import com.chero.client.vo.DoctorInfoVO;
import com.google.common.base.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hxh on 2018/5/17.
 */
@Component
public class DoctorInfo2DoctorInfoVOConverter extends Converter<DoctorInfo, DoctorInfoVO> {


    @Value("${cdn.image.baseurl}")
    private String cdnUrl;

    @Override
    protected DoctorInfoVO doForward(DoctorInfo doctorInfo) {
        DoctorInfoVO doctorInfoVO = new DoctorInfoVO();
        BeanUtils.copyProperties(doctorInfo, doctorInfoVO);

        String userface = doctorInfoVO.getUserface();
        if (userface != null && !"".equals(userface)) {
            doctorInfoVO.setUserface(cdnUrl + userface);
        }

        String idCertificatePhoto = doctorInfoVO.getIdCertificatePhoto();
        if (idCertificatePhoto !=null && !"".equals(idCertificatePhoto)) {
            doctorInfoVO.setIdCertificatePhoto(cdnUrl + idCertificatePhoto);
        }

        String registrationCertificatePhoto = doctorInfoVO.getRegistrationCertificatePhoto();
        if (registrationCertificatePhoto !=null && !"".equals(registrationCertificatePhoto)) {
            doctorInfoVO.setRegistrationCertificatePhoto(cdnUrl + registrationCertificatePhoto);
        }

        String titleCertificatePhoto = doctorInfoVO.getTitleCertificatePhoto();
        if (titleCertificatePhoto !=null && !"".equals(titleCertificatePhoto)) {
            doctorInfoVO.setTitleCertificatePhoto(cdnUrl + titleCertificatePhoto);
        }
        return doctorInfoVO;
    }

    @Override
    protected DoctorInfo doBackward(DoctorInfoVO doctorInfoVO) {
        throw new AssertionError("不支持逆向转化方法!");
    }
}
