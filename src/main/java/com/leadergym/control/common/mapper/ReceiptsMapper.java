package com.leadergym.control.common.mapper;

import com.leadergym.control.dto.ReceiptsResponseDTO;
import com.leadergym.control.entity.Receipts;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiptsMapper {
    public static List<ReceiptsResponseDTO> toDtoList(List<Receipts> receipts) {
        if (receipts == null) {
            return null;
        }
        return receipts.stream()
                .map(receipt -> {
                    ReceiptsResponseDTO dto = new ReceiptsResponseDTO();
                    dto.setFirstName(receipt.getMember().getFirstName());
                    dto.setLastName(receipt.getMember().getLastName());
                    dto.setDni(receipt.getMember().getDni());
                    dto.setDate(receipt.getDate());
                    // Mapear otros campos necesarios
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
