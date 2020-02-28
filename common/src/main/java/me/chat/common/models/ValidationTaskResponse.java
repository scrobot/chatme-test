package me.chat.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ValidationTaskResponse {

    private long id;
    private ValidationStatus status;
    private ValidationError error;

    public static ValidationTaskResponse fromException(Exception e) {
        return new ValidationTaskResponse()
            .setError(new ValidationError(e.getLocalizedMessage()));
    }

}
