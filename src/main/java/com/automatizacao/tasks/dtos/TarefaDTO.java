package com.automatizacao.tasks.dtos;

public record TarefaDTO(
        String idEmpresaGrupo,
        String idEmpresaGrupoArea,
        String idUsuarioResp,
        String idProjeto,
        String restricaoFlg,
        String titulo,
        String idUsuarioPara,
        String descricao,
        String ordem,
        String dataEntrega,
        String horasEstimadas,
        String idStatus,
        String idPrioridade
) {
}
