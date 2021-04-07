package com.dex.coreserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalEntityNotFoundExceptionResponse {

    private String notFound;
}
