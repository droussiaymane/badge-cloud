package com.vizzibl.service.tenant;

import com.vizzibl.dto.AddTenantDTO;
import com.vizzibl.dto.TenantDTO;
import com.vizzibl.entity.Tenant;
import com.vizzibl.repository.TenantRepository;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.utils.ConstantUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantServiceImpl implements TenantService {

    private TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public ResponseObject addTenant(AddTenantDTO addTenantDTO) {

        ResponseObject res;
        try {

            Tenant tenant = new Tenant();
            BeanUtils.copyProperties(addTenantDTO, tenant);
            tenant.setTenantId(UUID.randomUUID().toString());
            tenant.setCreationDate(LocalDateTime.now());
            tenantRepository.save(tenant);

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Tenant created successfully");
        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Internal server error");

        }

        return res;
    }

    @Override
    public ResponseObject getTenant(String tenantId) {

        ResponseObject res;
        try {
            TenantDTO tenantDTO = new TenantDTO();
            Optional<Tenant> optionalTenant = tenantRepository.findByTenantId(tenantId);

            if (optionalTenant.isPresent()) {
                BeanUtils.copyProperties(optionalTenant.get(), tenantDTO);
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", tenantDTO);
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "tenantId not found");
        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Internal server error");
        }
        return res;
    }
}
