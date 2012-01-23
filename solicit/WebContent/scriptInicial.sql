-- INSERINDO SETORES INICIAIS
INSERT INTO  setor
( id
, descricao
, chefe_matricula )
VALUES
( NULL
, 'Supervisão SOLICIT'
, NULL )
,
( NULL
, 'COINFO'
, NULL )
,
( NULL
, 'CORE'
, NULL )
,
( NULL
, 'Biblioteca Nilo Peçanha'
, NULL )
,
( NULL
, 'Reitoria'
, NULL )
,
( NULL
, 'Coordenação de Turno'
, NULL )
,
( NULL
, 'Coordenação de TSI'
, NULL )
,
( NULL
, 'Protocolos'
, NULL )
,
( NULL
, 'Manutenção'
, NULL )
,
( NULL
, 'Transportes'
, NULL )
,
( NULL
, 'Direção de Ensino'
, NULL )
,
( NULL
, 'Diretoria'
, NULL )
;


-- INSERINDO USUÁRIO SUPERVISOR
INSERT INTO servidor
( matricula
, cpf
, email
, md5
, nivelAcesso
, nome
, setor_id )
VALUES
( '000000'
, '000.000.000-00'
, 'supervisor@solicit.ifpb.edu.br'
, '670b14728ad9902aecba32e22fa4f6bd'
, 2
, 'Supervisor SOLICIT'
, 1 )
;

-- TIPOS DE SOLICITAÇÃO
INSERT INTO tiposolicitacao
( id
, descricao )
VALUES
( NULL
, 'Férias' )
,
( NULL
, 'Abono salarial' )
,
( NULL
, 'Adicional de insalubridade ou periculosidade' )
,
( NULL
, 'Reserva de sala' )
,
( NULL
, 'Licença médica' )
,
( NULL
, 'Licença maternidade' )
,
( NULL
, 'Licença paternidade' )
,
( NULL
, 'Transferência interna' )
,
( NULL
, 'Transferência externa' )
;