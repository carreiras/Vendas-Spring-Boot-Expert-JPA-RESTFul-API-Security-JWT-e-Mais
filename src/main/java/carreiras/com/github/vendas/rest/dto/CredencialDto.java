package carreiras.com.github.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredencialDto {

    private String login;
    private String senha;
}
