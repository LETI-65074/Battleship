# Test Suite: Operations checklist

TMS-ID: TS-OPS-001
Tags: #checklist #reports #unit-tests

* Gerar relatório de cobertura de código com "Run with Coverage" no IntelliJ para todos os testes em `iscteiul.ista.battleship`
* Guardar o relatório de cobertura HTML na pasta `reports` com o nome que inclua o utilizador GitHub LETI-65074
* Executar todos os testes unitários (`BargeTest`, `CaravelTest`, `CarrackTest`, `CompassTest`, `FleetTest`, `FrigateTest`, `GalleonTest`, `GameTest`, `PositionTest`, `ShipTest`, `AppTest`) antes de cada `git push`
* Confirmar que o workflow do GitHub Actions correu os testes com sucesso após cada `git push`
* Exportar os resultados dos testes (Run → Export Test Results) para a pasta `reports/tests`
* Criar uma tag no último commit da entrega (ex.: `v1.0` ou `entrega-final`) e fazer `git push origin --tags`
* Abrir o Pull Request final no GitHub a partir do ramo da entrega
