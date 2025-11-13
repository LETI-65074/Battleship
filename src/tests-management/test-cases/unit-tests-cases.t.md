# Unit Tests – Battleship
Tags: unit-tests, test-cases
Meta: TMS-ID = TESTSUITE-001, owner = snoye

## S1 Modelo de domínio
* C1 Ship – barco genérico
  Tags: ship, core
    * Testes sobre a classe Ship (barco genérico)
* C2 Position e Compass
  Tags: position, compass
    * Testes sobre Position e Compass

## S2 Tamanhos de barcos
* C3 Barcos de tamanho 1, 2 e 3
  Tags: ships-size-1-3
    * Criar barcos de tamanho 1, 2 e 3
    * Verificar posições ocupadas e stillFloating
* C4 Barcos de tamanho 4 e 5
  Tags: ships-size-4-5
    * Criar barcos de tamanho 4 e 5
    * Verificar posições ocupadas e stillFloating

## S3 Frota e UI
* C5 Frota (Fleet/Board)
  Tags: fleet, board
    * Adicionar barcos à frota
    * Verificar colisões / proximidade / todos afundados
* C6 Tasks (interação com utilizador)
  Tags: tasks, ui
    * Testes sobre Tasks – separados dos restantes
