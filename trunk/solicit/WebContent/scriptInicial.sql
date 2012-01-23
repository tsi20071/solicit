-- INSERINDO SETORES INICIAIS
INSERT INTO  setor
( id
, descricao
, chefe_matricula )
VALUES
( NULL
, 'Supervis�o SOLICIT'
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
, 'Biblioteca Nilo Pe�anha'
, NULL )
,
( NULL
, 'Reitoria'
, NULL )
,
( NULL
, 'Coordena��o de Turno'
, NULL )
,
( NULL
, 'Coordena��o de TSI'
, NULL )
,
( NULL
, 'Protocolos'
, NULL )
,
( NULL
, 'Manuten��o'
, NULL )
,
( NULL
, 'Transportes'
, NULL )
,
( NULL
, 'Dire��o de Ensino'
, NULL )
,
( NULL
, 'Diretoria'
, NULL )
;


-- INSERINDO USU�RIO SUPERVISOR
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

-- TIPOS DE SOLICITA��O
INSERT INTO tiposolicitacao
( id
, descricao )
VALUES
( NULL
, 'F�rias' )
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
, 'Licen�a m�dica' )
,
( NULL
, 'Licen�a maternidade' )
,
( NULL
, 'Licen�a paternidade' )
,
( NULL
, 'Transfer�ncia interna' )
,
( NULL
, 'Transfer�ncia externa' )
;