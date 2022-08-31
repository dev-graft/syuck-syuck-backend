package devgraft.apidoc;

import devgraft.code.api.CodeApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CodeApi.class)
public class CodeApiDoc extends AbstractApiDoc {

    @DisplayName("공개키 초기화 요청")
    @Test
    void initCode() throws Exception {
        mockMvc.perform(get("/api/code"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data.pubKey").type(JsonFieldType.STRING).description("Base64(공개키)")
                        )
                ));
    }

    @DisplayName("기본 프로필 이미지 요청")
    @Test
    void getDefaultProfileImage() throws Exception {
        mockMvc.perform(get("/api/code/profile"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("프로필 이미지 URL")
                        )
                ));
    }
}
