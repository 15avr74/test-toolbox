# language: fr
#Author: Alex. Vieira Ramos
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Fonctionnalité: Générer un rapport d'accessibilité

Plan du scénario: Analyser l'accessibilité d'une page
	Etant donné Un utilisateur ayant les droits de parcourir l'application
	Quand   		Il ouvre la page <url>
	Et 					analyse cette page 
	Alors 			un rapport de synthèse est généré
      
| url  |
| accueil.xhtml |
      
